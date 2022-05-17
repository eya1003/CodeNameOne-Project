<?php

namespace App\Controller;

use App\Entity\Commande;
use App\Entity\Emplacement;
use App\Entity\LigneCommande;
use App\Entity\Reservation;
use App\Entity\Table;
use App\Form\BackResvType;
use App\Form\FrontResvType;
use App\Form\ReservationBackType;
use App\Form\ResvType;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\Method;
use App\Repository\ReservationRepository;
use Doctrine\ORM\EntityManagerInterface;
use Knp\Component\Pager\PaginatorInterface;
use phpDocumentor\Reflection\Types\Integer;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\Form\Extension\Core\Type\SubmitType;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
//use App\Controller\NormalizerInterface;
use Symfony\Component\Form\Extension\Core\Type\ChoiceType;

use Symfony\Component\Serializer\Normalizer\NormalizerInterface;


class ReservationBackController extends AbstractController
{
    /**
     * @Route("/reservation/back", name="reservation_back")
     */
    public function index(PaginatorInterface $paginator, ReservationRepository $reservationRepository , Request $request): Response


    {
        $res = $paginator->paginate(
        // Doctrine Query, not results
            $reservationRepository->findAll(),
            // Define the page parameter
            $request->query->getInt('page', 1),
            // Items per page
            3
        );

        return $this->render('reservation_back/index.html.twig', [
            'tabResv' => $res,
        ]);
    }
    /**
     * @Route ("/AllResv", name="AllResv")
     */
    public function AllResv(ReservationRepository $repo,NormalizerInterface $Normalizer)
    {

        $reservation=$this->getDoctrine()->getRepository(Reservation::class)->findAll();

        $jsonContent=$Normalizer->normalize($reservation,'json',['groups'=>'post:read']);


        return new Response(json_encode($jsonContent));

    }

        /**
     * @Route("/updateReservatio,/{id}",name="modifierResv")
     */
    public function update(Request $request,$id, ReservationRepository $repo,NormalizerInterface  $normalizer)
    {
        $res= $this->getDoctrine()
            ->getRepository(Reservation::class)->find($id);

        $formResv= $this->createForm(ReservationBackType::class,$res,array("allow_extra_fields"=>true));
        $resv= $this->getDoctrine()
            ->getRepository(Table::class)->distinctVue();
        $karray = array();
        foreach($resv as $x => $val) {
            $rer = $this -> getDoctrine()
                -> getRepository(Emplacement::class) -> find(array_values($val)[0]);
            $karray[$rer -> getTypeEmplacement()] = array_values($val)[0];
        }
        $formResv->add('emplacement', ChoiceType::class, array(
            "mapped" => false,
            "attr" => array(
                'class' => "form-control"
            ),
                'choices'  => $karray
        ));

        $formResv->add('Enregistrer',SubmitType::class);

        $formResv->handleRequest($request);

        if($formResv->isSubmitted()&& $formResv->isValid()) {
            $emplacement = $formResv->get("emplacement")->getData();
            $emp= $this->getDoctrine()
                ->getRepository(Table::class)->findOneBy(array("emp"=>$emplacement));

            $em= $this->getDoctrine()->getManager();
            $res->setTabResv($emp);
            $em->flush();
            $jsonContent =$normalizer->normalize($emp,'json',['groups'=>'post:read']);
            return new Response("Reservation updated successfully".json_encode($jsonContent));
            return $this->redirectToRoute("reservation_back", [],Response::HTTP_SEE_OTHER);
        }
            return $this->render("reservation_back/updateResv.html.twig",array("formResv"=>$formResv->createView(),"eya"=>$resv));

       /* $res= $this->getDoctrine()
            ->getRepository(Reservation::class)->find($id);

        $formResv= $this->createForm(FrontResvType::class,$res);
        $formResv->handleRequest($request);

        if($formResv->isSubmitted()&& $formResv->isValid()) {

            $em= $this->getDoctrine()->getManager();
            $em->flush();
            return $this->redirectToRoute("reservation_back", [],Response::HTTP_SEE_OTHER);
        }
        return $this->render("reservation_back/updateResv.html.twig",array("formResv"=>$formResv->createView()));
*/
    }




    /**
     * @Route("/reservation/supprimer/{id}",name="supprimerResv")
     */
    public function supprimer(ReservationRepository $c,$id,EntityManagerInterface $em,NormalizerInterface  $normalizer)
    {
        $emp= $c->find($id);
        $em->remove($emp);
        $em->flush();
        $this->addFlash('clear', 'une reservation est supprimée');
        return $this->redirectToRoute("reservation_back");
    }
    //recherche
    /*
    /**
    * @Route("/search/Reservation", name="search_Reservation", requirements={"id_resv":"\d+"})
    */
    /*
    public function searchReservation(Request $request, NormalizerInterface $Normalizer)
²   {
    $repository = $this->getDoctrine()->getRepository(Reservation::class);
    $requestString = $request->get('searchValue');
    $res = $repository->findResByVue($requestString);
    $jsonContent = $Normalizer->normalize($res, 'json',[]);

    return new Response(json_encode($jsonContent));
²   }
    */


    /**
     * @Route("/admin/sortprix", name="sortprix")
     */
    public function trip(): Response{
        $resv= $this->getDoctrine()->getRepository(Reservation::class)->sortPhone();
        return $this->render('reservation_back/SortedPrix.html.twig', [
            'controller_name' => ' ReservationBackController ',
            'phonesorted' => $resv,
        ]);
    }


    /**
     * Creates a new ActionItem entity.
     *
     * @Route("/search", name="ajax_search")
     * @Method("GET")
     */
    public function s (Request $request): Response
    {
        $em = $this->getDoctrine()->getManager();

        $requestString = $request->get('q');

        $entities =  $em->getRepository(Reservation::class)->findEntitiesByString($requestString);

        if(!$entities) {
            $result['class']['error'] = "aucune entitee a ete trouvee";
        } else {
            $result['class'] = $this->getRealEntities($entities);
        }

        return new Response(json_encode($result));
    }

    public function getRealEntities($entities){

        foreach ($entities as $entity){
            $realEntities[$entity->getEmailResv()] = [$entity->getPhoneResv(),$entity->getgetIdResv(),$entity->getTabResv()->getEmp()];
        }

        return $realEntities;
    }

    /**
     * @Route("/list/{id_resv}",name="list")
     */
    public function show1(Reservation $Reservation): Response
    {
        return $this->render('reservation_back/list.html.twig',['Reservation'=>$Reservation]);
    }


    /**
     * @Route("/supprimerResv/{id}",name="supprimerReservation")
     */
    public function supprimerMobile(Request $request, NormalizerInterface  $normalizer, $id)

    {
        $em = $this -> getDoctrine() -> getManager();
        $resv = $em -> getRepository(Reservation::class) -> find($id);
        $em -> remove($resv);
        $em -> flush();
        $jsonContent = $normalizer -> normalize($resv, 'json', ['groups' => 'post:read']);
        return new Response("Reservation deleted successfully" . json_encode($jsonContent));


    }

    /**
     * @Route("/reserupdate/{id}",name="updateResv", methods={"GET","POST"})
     */
    public function updateJSONMobile(Request $request,$id,NormalizerInterface  $normalizer)
    {
        $em= $this->getDoctrine()->getManager();
        $emp= $this->getDoctrine()
            ->getRepository(Reservation::class)->find($id);

        $emp->setPhoneResv($request->get('phone_resv'));
        $emp->setEmailResv($request->get('Email_resv'));
        $em->flush();
        $jsonContent =$normalizer->normalize($emp,'json',['groups'=>'post:read']);
        return new Response("Information updated successfully".json_encode($jsonContent));
    }
}
