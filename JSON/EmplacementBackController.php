<?php

namespace App\Controller;

use App\Entity\Emplacement;
use App\Entity\Reservation;
use App\Form\EmplacementBackType;
use App\Repository\EmplacementRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\HttpFoundation\Request;
use App\Repository\TableRepository;
 use Doctrine\ORM\EntityManagerInterface;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\Serializer\Encoder\JsonEncoder;
use Symfony\Component\Serializer\Normalizer\ObjectNormalizer;
use Symfony\Component\Serializer\Serializer;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\Method;
use Symfony\Component\Validator\Constraints\Json;
use Symfony\Component\Serializer\Normalizer\NormalizerInterface;
class EmplacementBackController extends AbstractController
{
    /**
     * @Route("/emplacement/back", name="liste_emplacements")
     */
    public function index(): Response
    {
        $emp= $this->getDoctrine()
            ->getRepository(Emplacement::class)->findAll();
        return $this->render('emplacement_back/index.html.twig',
            array("tabEmp"=>$emp));
    }

    /**
     * @Route ("/AllEmp", name="AllEmp")
     */
    public function AllEmp(NormalizerInterface  $normalizer)
    {
        $repository = $this->getDoctrine()->getRepository(Emplacement::class);
        $tables =  $repository->findAll();
        $jsonContent = $normalizer->normalize($tables, 'json',['groups'=>'post:read']);
        return $this->render('emplacement_back/allEmpJSON.html.twig', [
            'data'=> $jsonContent,
        ]);

        return new Response(json_encode($jsonContent));

    }

    /**
     * @Route("/mobile/addEmpJSON", name="Emplacement")
     */
    public function newEmp(Request $request,NormalizerInterface  $normalizer)
    {
        $em= $this->getDoctrine()->getManager();
        $emp= new Emplacement();
        $emp->setTypeEmplacement($request->get('type_emplacement'));
        $emp->setDescription($request->get('Description'));
        $em->persist($emp);
        $em->flush();
        $jsonContent =$normalizer->normalize($emp,'json',['groups'=>'post:read']);
        return new Response(json_encode($jsonContent));
    }


    /**
     * @Route("/mobile/api/afficheEmp", name="api_afficheEmp")
     */
    public function AfficheApi(EmplacementRepository $repo,NormalizerInterface $Normalizer) {
        $agence=$this->getDoctrine()->getRepository(Emplacement::class)->findAll();

        $jsonContent=$Normalizer->normalize($agence,'json',['groups'=>'post:read']);


        return new Response(json_encode($jsonContent));

    }
    /**
     * @Route("/addEmp", name="addEmp", methods={"POST"})
     */
    public function new(Request $request,NormalizerInterface  $normalizer)
    {
        $emp= new Emplacement();
        $formEmp= $this->createForm(EmplacementBackType::class,$emp);
        $formEmp->handleRequest($request);
        if($formEmp->isSubmitted() && $formEmp->isValid()){
            $em= $this->getDoctrine()->getManager();
            $em->persist($emp);
            $em->flush();
            return $this->redirectToRoute("liste_emplacements", [],Response::HTTP_SEE_OTHER);
        }
        return $this->render("emplacement_back/addEmp.html.twig",array("formEmp"=>$formEmp->createView()));
    }

    /**
     * @Route("/mobile/emplacement/updateEmplacement/{id}",name="updateEmp", methods={"GET","POST"})
     */
    public function update(Request $request,$id,NormalizerInterface  $normalizer)
    {
        $em= $this->getDoctrine()->getManager();
        $emp= $this->getDoctrine()
            ->getRepository(Emplacement::class)->find($id);

        $emp->setTypeEmplacement($request->get('type_emplacement'));
        $emp->setDescription($request->get('Description'));
        $em->flush();
            $jsonContent =$normalizer->normalize($emp,'json',['groups'=>'post:read']);
            return new Response("Information updated successfully".json_encode($jsonContent));
    }



    /**
     * @Route("/mobile/supprimerEmp/{id}",name="supprimerEmp")
     */
    public function supprimer(Request $request, NormalizerInterface  $normalizer, $id)

    {
        $em = $this -> getDoctrine() -> getManager();
        $emplacement = $em -> getRepository(Emplacement::class) -> find($id);
        $em -> remove($emplacement);
        $em -> flush();
        $jsonContent = $normalizer -> normalize($emplacement, 'json', ['groups' => 'post:read']);
        return new Response("Information deleted successfully" . json_encode($jsonContent));


    }



    }
