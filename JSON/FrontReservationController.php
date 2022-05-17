<?php

namespace App\Controller;

use App\Entity\Emplacement;
use App\Entity\Reservation;
use App\Entity\Table;
use App\Form\BackResvType;
use App\Form\FrontResvType;
use App\Form\ResvType;
use App\Repository\ReservationRepository;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\Form\Extension\Core\Type\ChoiceType;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\Serializer\Normalizer\NormalizerInterface;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\Serializer\Encoder\JsonEncoder;
use Symfony\Component\Serializer\Normalizer\ObjectNormalizer;
use Symfony\Component\Serializer\Serializer;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\Method;
use Symfony\Component\Validator\Constraints\Json;

class FrontReservationController extends AbstractController
{
    /**
     * @Route("/front/reservation", name="reservation")
     */
    public function index(): Response
    {
        return $this->render('front_reservation/index.html.twig', [
            'controller_name' => 'FrontReservationController',
        ]);
    }




    /**
     * @Route("/add/reservation", name="front_reservation", methods={"GET","POST"})
     */
    public function new(Request $request)
    {
        /*
        $table= new Reservation();
        $formResv= $this->createForm(FrontResvType::class,$table,array("allow_extra_fields"=>true));
        $resv= $this->getDoctrine()
            ->getRepository(Table::class)->distinctVue();
        $karray = array();
        foreach($resv as $x => $val) {
            $rer = $this->getDoctrine()
                ->getRepository(Emplacement::class)->find(array_values($val)[0]);
            $karray[$rer->getTypeEmplacement()] = array_values($val)[0];
        }
        $formResv->add('emplacement', ChoiceType::class, array(
            "mapped" => false,
            "attr" => array(
                'class' => "form-control"
            ),
            'choices'  => $karray
        ));

        $formResv->handleRequest($request);
        if($formResv->isSubmitted() && $formResv->isValid()){
            $emplacement = $formResv->get("emplacement")->getData();
            $emp= $this->getDoctrine()
                ->getRepository(Table::class)->findOneBy(array("emp"=>$emplacement));

            $em= $this->getDoctrine()->getManager();
            $em->persist($table);
            $em->flush();

            return $this->redirectToRoute("reservation_back", [],Response::HTTP_SEE_OTHER);
            while ($stock_resv >= 0) {
                --$stock_resv;
                break;
            }
        }
        return $this->render("front_reservation/addResv.html.twig",array("formResv"=>$formResv->createView(),"eya"=>$resv));
    }*/
        $table= new Reservation();

        $formResv= $this->createForm(FrontResvType::class,$table);
        $formResv->handleRequest($request);
        if($formResv->isSubmitted() && $formResv->isValid()){
            $em= $this->getDoctrine()->getManager();
            $this->addFlash('info',
                'add successufuly'
            );
         //   $stock_resv = $formResv->get("stock_resv")->getData();
          //  $emp= $this->getDoctrine()->getManagers()
            //    ->getRepository(Reservation::class)->findOneBy(array("stock_resv"=>$stock_resv));

            $em->persist($table);
            $em->flush();
           // $stock =new Reservation();
          //  $stock->getStockResv() -1 ;
          //  $emp->setStockResv($stock);
          //  $em->persist($emp);


            return $this->redirectToRoute("reservation_back", [],Response::HTTP_SEE_OTHER);
        }
        return $this->render("front_reservation/addResv.html.twig",array("formResv"=>$formResv->createView()));


    }
/**
* @Route("/mobile/addResvJSON", name="Resv")
 * @Method("POST")
*/
    public function newResv(Request $request,NormalizerInterface  $normalizer)
    {
        $reservation = new Reservation();
        $phone= $request->query->get("phone_resv");
        $email = $request->query->get("Email_resv");
        $em = $this->getDoctrine()->getManager();
        $date = new \DateTime('now');
        $datEnd = new \DateTime('now+1 hour');



        $reservation->setDateResv($date);
        $reservation->setEndResv($datEnd);
        $reservation->setPhoneResv($phone);
        $reservation->setEmailResv($email);

        $em->persist($reservation);
        $em->flush();
        $serializer = new Serializer([new ObjectNormalizer()]);
        $jsonContent =$normalizer->normalize($reservation,'json',['groups'=>'post:read']);
        return new Response(json_encode($jsonContent));
    }

}
