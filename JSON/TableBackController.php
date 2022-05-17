<?php

namespace App\Controller;

use App\Entity\Emplacement;
use App\Entity\Reservation;
use App\Entity\Table;
use App\Form\TableBackType;
use App\Repository\TableRepository;
use App\Repository\EmplacementRepository;
use Doctrine\ORM\EntityManagerInterface;
use Psr\Container\ContainerInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\HttpFoundation\File;
use Symfony\Component\Serializer\Normalizer\NormalizerInterface;

use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\Serializer\Encoder\JsonEncoder;
use Symfony\Component\Serializer\Normalizer\ObjectNormalizer;
use Symfony\Component\Serializer\Serializer;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\Method;
use Symfony\Component\Validator\Constraints\Json;


class TableBackController extends AbstractController
{
    /**
     * @Route("/back/table", name="list_tables")
     */
    public function index(TableRepository $tableRepository): Response
    {
        $table= $this->getDoctrine()
            ->getRepository(Table::class)->findAll();
        return $this->render('table_back/index.html.twig',
            array("tabTable"=>$table));
    }


    /**
     * @Route ("/AllTable", name="AllTable")
     */
    public function AllTable(NormalizerInterface  $normalizer)
    {
        $repository = $this->getDoctrine()->getRepository(Table::class);
        $tables =  $repository->findAll();
        $jsonContent = $normalizer->normalize($tables, 'json',['groups'=>'post:read']);
        return $this->render('table_back/allTableJSON.html.twig', [
            'data'=> $jsonContent,
        ]);

        return new Response(json_encode($jsonContent));

    }

    /**
     * @Route("/displayTables", name="display_")
     */
    public function allRecAction()
    {

        $reclamation = $this->getDoctrine()->getManager()->getRepository(Table::class)->findAll();
        $serializer = new Serializer([new ObjectNormalizer()]);
        $formatted = $serializer->normalize($reclamation);

        return new JsonResponse($formatted);

    }

    /**
     * @Route("/addTable", name="addTable", methods={"GET","POST"})
     */
    public function new(Request $request,NormalizerInterface  $normalizer)
    {
        $table= new Table();
        $formTable= $this->createForm(TableBackType::class,$table);
        $formTable->handleRequest($request);
        if($formTable->isSubmitted() && $formTable->isValid()){
            $em= $this->getDoctrine()->getManager();
            $em->persist($table);
            $em->flush();

          return $this->redirectToRoute("list_tables", [],Response::HTTP_SEE_OTHER);
        }

        return $this->render("table_back/addTable.html.twig",array("formTable"=>$formTable->createView()));
    }


    /**
     * @Route("/mobile/affTable", name="affTable")
     */
    public function affTable(TableRepository $repo,NormalizerInterface $Normalizer) {

        $table=$this->getDoctrine()->getRepository(Table::class)->findAll();
        $jsonContent=$Normalizer->normalize($table,'json',['groups'=>'post:read']);
        return new Response(json_encode($jsonContent));

    }

    /**
     * @Route("/table/supprimer/{id}",name="supprimerTab")
     */
    public function supprimer(TableRepository $c,$id,EntityManagerInterface $em,NormalizerInterface  $normalizer)
    {
        $emp= $c->find($id);
        $em->remove($emp);
        $em->flush();
        $jsonContent =$normalizer->normalize($emp,'json',['groups'=>'post:read']);
        return new Response("Information deleted successfully".json_encode($jsonContent));
    }

    /**
     * @Route("/StatCommande", name="StatCommande")
     */
    public function Statab(): Response
    {

        $statReservations = $this->getDoctrine()->getRepository(Table::class)->findAll();
        $stock_tab= [];
        $emp = [];
        foreach ($statReservations as $statReservation) {
            $stock_tab[] = $statReservation->getStockTab();
            $emp [] = $statReservation->getEmp()->getTypeEmplacement();
        }
        return $this->render('table_back/stattable.html.twig', [
            'stock_tab' => json_encode($stock_tab),
            'emp' => json_encode($emp)
        ]);
    }


    /**
     * @Route("/addTableJSON", name="Table")
     * @Method ("POST")
     */
    public function newTable(Request $request,NormalizerInterface  $normalizer)
    {

        $table= new Table();
        $em= $this->getDoctrine()->getManager();
        $table->setNbChaiseTab($request->get('nb_chaise_tab'));
        $table->setStockTab($request->get('stock_tab'));

        $em->persist($table);
        $em->flush();
        $jsonContent =$normalizer->normalize($table,'json',['groups'=>'post:read']);
        return new Response(json_encode($jsonContent));
    }

    /**
     * @Route("/table/updateTable/{id}",name="updateTabMob", methods={"GET","POST"})
     */
    public function update(Request $request,$id,NormalizerInterface  $normalizer)
    {

        $em= $this->getDoctrine()->getManager();
        $emp= $this->getDoctrine()
            ->getRepository(Table::class)->find($id);

        $emp->setNbChaiseTab($request->get('nb_chaise_tab'));
        $emp->setStockTab ($request->get('stock_tab'));

        $em->flush();
        $jsonContent =$normalizer->normalize($emp,'json',['groups'=>'post:read']);
        return new Response("Information updated successfully".json_encode($jsonContent));
    }

    /**

     * @Route("addReclamationJson/new", name="add_reclamation" ,methods = {"GET", "POST"})
     */


    public function  addReclamation(Request $request,NormalizerInterface  $normalizer)
    {
        $Reclamation = new Table() ;

        $chaise = $request->query->get( "nb_chaise_tab" ) ;
        $em = $this->getDoctrine()-> getManager();
        $vues= $request->query->get("emp") ;
        $stocks =$request->query-> get ("stock_tab") ;


        $Reclamation->setNbChaiseTab ( $chaise ) ;
        $Reclamation-> setEmp($this->getDoctrine()->getManager()->getRepository(Emplacement::class)->find($vues));

        $Reclamation-> setStockTab ( $stocks ) ;
        $em-> persist ( $Reclamation ) ;
        $em-> flush () ;
      /*  $encoder = new JsonEncoder () ;
        $normalizer = new ObjectNormalizer ( ) ;
        $normalizer-> setCircularReferenceHandler ( function ($object) {
            return $object ;
        });
        $serializer = new Serializer ( [$normalizer] , [$encoder] ) ;
        $formatted = $serializer->normalize ( $Reclamation ) ;
        return new JsonResponse ( $formatted ) ;*/
        $jsonContent =$normalizer->normalize($Reclamation,'json',['groups'=>'post:read']);
        return new Response("Information updated successfully".json_encode($jsonContent));

    }
}
