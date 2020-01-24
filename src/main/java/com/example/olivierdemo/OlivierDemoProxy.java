package com.example.olivierdemo;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "forest", url = "localhost:9104")
public interface OlivierDemoProxy {
	/*
	 * 
	 * OBJECT FOREST
	 * 
	 */
	@GetMapping(value = "/forestlist")
	List<Forest> forestlist();

	@PostMapping("/forestadd")
	Forest createforest(@RequestBody Forest forest);

	@GetMapping(value = "/forestview/{id}")
	Forest forestview(@PathVariable("id") int id);

	/*
	 * 
	 * OBJECT PARCELLE FORESTIERE
	 * 
	 */
	@GetMapping(value = "/parcelleforestierelist")
	List<ParcelleForestiere> parcelleforestierelist();

	@PostMapping("/parcelleforestiereadd")
	ParcelleForestiere createparcelleforestiere(@RequestBody ParcelleForestiere parcelleforestiere);

	@GetMapping(value = "/parcelleforestiereview/{id}")
	ParcelleForestiere parcelleforestiereview(@PathVariable("id") int id);

	/*
	 * 
	 * OBJECT PARCELLE CADASTRALE
	 * 
	 */
	@GetMapping(value = "/parcellecadastralelist")
	List<ParcelleCadastrale> parcellecadastralelist();

	@PostMapping("/parcellecadastraleadd")
	ParcelleCadastrale createparcellecadastrale(@RequestBody ParcelleCadastrale parcellecadastrale);

	@GetMapping(value = "/parcellecadastraleview/{id}")
	ParcelleCadastrale parcellecadastraleview(@PathVariable("id") int id);

	@GetMapping(value = "/parcellecadastralegetpeuplement/{id}")
	List<Peuplement> parcellecadastralegetpeuplement(@PathVariable("id") int id);

	@GetMapping(value = "/parcellecadastraleviewfindByNumeroparcelle/{parcelle}")
	ParcelleCadastrale parcellecadastraleviewfindByNumeroparcelle(@PathVariable("parcelle") String parcelle);

	/*
	 * 
	 * OBJECT PEUPLEMENT
	 * 
	 */
	@GetMapping(value = "/peuplementlist")
	List<Peuplement> peuplementlist();

	@PostMapping("/peuplementadd")
	Peuplement createpeuplement(@RequestBody Peuplement peuplement);

	@GetMapping(value = "/peuplementview/{id}")
	Peuplement peuplementview(@PathVariable("id") int id);

	@GetMapping(value = "/findByUniteforestiereAndParcellecadastrale/{unite}/{numeroparcelle}")
	Peuplement findByUniteforestiereAndParcellecadastrale(@PathVariable String unite,
	@PathVariable String numeroparcelle);

	/*
	 * 
	 * OBJECT TYPE PEUPLEMENT
	 * 
	 */
	@GetMapping(value = "/typepeuplementlist")
	List<TypePeuplement> typepeuplementlist();

	@PostMapping("/typepeuplementadd")
	TypePeuplement createtypepeuplement(@RequestBody TypePeuplement typepeuplement);

	@GetMapping(value = "/typepeuplementview/{id}")
	TypePeuplement typepeuplementview(@PathVariable("id") int id);

	/*
	 * 
	 * OBJECT STATION FORESTIERE
	 * 
	 */
	@GetMapping(value = "/stationforestierelist")
	List<StationForestiere> stationforestierelist();

	@PostMapping("/stationforestiereadd")
	StationForestiere createstationforestiere(@RequestBody StationForestiere stationforestiere);

	@GetMapping(value = "/stationforestiereview/{id}")
	StationForestiere stationforestiereview(@PathVariable("id") int id);

	/*
	 * 
	 * OBJECT ESSENCE
	 * 
	 */
	@GetMapping(value = "/essencelist")
	List<Essence> essencelist();

	@PostMapping("/essenceadd")
	Essence createessence(@RequestBody Essence essence);

	@GetMapping(value = "/essenceview/{id}")
	Essence essenceview(@PathVariable("id") int id);

	/*
	 * 
	 * OBJECT OPERATION SYLVICOLE
	 * 
	 */
	@GetMapping(value = "/operationsylvicolelist")
	List<OperationSylvicole> operationsylvicolelist();

	@PostMapping("/operationsylvicoleadd")
	OperationSylvicole createoperationsylvicole(@RequestBody OperationSylvicole operationsylvicole);

	@GetMapping(value = "/operationsylvicoleview/{id}")
	OperationSylvicole operationsylvicoleview(@PathVariable("id") int id);

	/*
	 * 
	 * OBJECT TYPE TRAVAUX
	 * 
	 */
	@GetMapping(value = "/typetravauxlist")
	List<TypeTravaux> typetravauxlist();

	@PostMapping("/typetravauxadd")
	TypeTravaux createtypetravaux(@RequestBody TypeTravaux typetravaux);

	
	@GetMapping(value = "/typetravauxview/{id}")
	TypeTravaux typetravauxview(@PathVariable("id") int id);

	/*
	 * 
	 * OBJECT PROGRAMMATION
	 * 
	 */
	@GetMapping(value = "/programmationlist")
	List<Programmation> programmationlist();

	@PostMapping("/programmationadd")
	public ResponseEntity<String>  createprogrammation(@RequestBody Programmation programmation);
	@PostMapping("/programmationadd1")
	Programmation  createprogrammation1(@RequestBody Programmation programmation);

	@GetMapping(value = "/programmationview/{id}")
	Programmation programmationview(@PathVariable("id") int id);
}
