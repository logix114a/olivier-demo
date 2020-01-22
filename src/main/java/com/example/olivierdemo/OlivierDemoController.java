package com.example.olivierdemo;

import java.io.IOException;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;

import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;



@Controller
@RequestMapping("/")
public class OlivierDemoController {
	private StorageService storageService;

	@Autowired
	public void FileUploadController(StorageService storageService) {
		this.storageService = storageService;
	}
	
	@Autowired
    private OlivierDemoProxy OlivierDemoProxy;	
	

	private static final Logger logger = LoggerFactory.getLogger(OlivierDemoController.class);
	
	@GetMapping("")
	public ModelAndView home() {
		return new ModelAndView("home");
	}
	@GetMapping("home")
	public ModelAndView home1() {
		return new ModelAndView("home");
	}	
	@GetMapping("home-top")
	public ModelAndView hometop() {
		return new ModelAndView("home-top");
	}
	
	@GetMapping("registration")
	public ModelAndView registration() {
		return new ModelAndView("registration");
	}
	/*
	 * 
	 * OBJECT FOREST
	 * 
	 */
	@GetMapping("forestlist")
	public ModelAndView myforest() {
		/*Sort test = new Sort(Sort.Direction.ASC, "id");*/
		Iterable<Forest> forests = OlivierDemoProxy.forestlist();
		return new ModelAndView("forestlist", "forests", forests);	 
	}	
	
	@GetMapping(path = "forestadd")
	public ModelAndView forestadd(Forest forest) {
		return new ModelAndView("forestadd");
	}
	@PostMapping(path = "forestadd")
	public ModelAndView forestadd(@Valid Forest forest, BindingResult result, RedirectAttributes redirect) {		
		OlivierDemoProxy.createforest(forest);
		return new ModelAndView("redirect:forestlist");
	}


	@GetMapping(path = "forestview/{id}")
	public ModelAndView viewforest(@PathVariable("id") int id) {
		Forest forest  = OlivierDemoProxy.forestview(id);
		return new ModelAndView("forestview", "forest", forest);
	}
	
	
	@GetMapping(path = "forestmodify/{id}") // Map ONLY GET Requests
	public ModelAndView forestmodify(@PathVariable("id") int id) {
		Forest forest  = OlivierDemoProxy.forestview(id);
		return new ModelAndView("forestmodify", "forest", forest);
	}
	
	
	@PostMapping(path = "forestmodify")
	public ModelAndView forestmodify(@Valid Forest forest, BindingResult result, RedirectAttributes redirect) {
		OlivierDemoProxy.createforest(forest);
		return new ModelAndView("redirect:forestlist");
	}
	
	/*
	 * 
	 * OBJECT PARCELLE FORESTIERE
	 * 
	 */
	@GetMapping("parcelleforestierelist")
	public ModelAndView parcelleforestierelist() {
		/*Sort test = new Sort(Sort.Direction.ASC, "id");*/
		Iterable<ParcelleForestiere> parcelleforestieres = OlivierDemoProxy.parcelleforestierelist();
		return new ModelAndView("parcelleforestierelist", "parcelleforestieres", parcelleforestieres);	 
	}	
	
	@GetMapping(path = "parcelleforestiereadd")
	public ModelAndView parcelleforestiereadd(ParcelleForestiere parcelleforestiere) {
		return new ModelAndView("parcelleforestiereadd");
	}
	@PostMapping(path = "parcelleforestiereadd")
	public ModelAndView parcelleforestiereadd(@Valid ParcelleForestiere parcelleforestiere, BindingResult result, RedirectAttributes redirect) {		
		OlivierDemoProxy.createparcelleforestiere(parcelleforestiere);
		return new ModelAndView("redirect:myforest");
	}


	@GetMapping(path = "parcelleforestiereview/{id}")
	public ModelAndView viewparcelleforestiere(@PathVariable("id") int id) {
		ParcelleForestiere parcelleforestiere  = OlivierDemoProxy.parcelleforestiereview(id);
		return new ModelAndView("parcelleforestiereview", "parcelleforestiere", parcelleforestiere);
	}
	
	
	@GetMapping(path = "parcelleforestieremodify/{id}") // Map ONLY GET Requests
	public ModelAndView parcelleforestieremodify(@PathVariable("id") int id) {
		ParcelleForestiere parcelleforestiere  = OlivierDemoProxy.parcelleforestiereview(id);
		return new ModelAndView("parcelleforestieremodify", "parcelleforestiere", parcelleforestiere);
	}
	
	
	@PostMapping(path = "parcelleforestieremodify")
	public ModelAndView parcelleforestieremodify(@Valid ParcelleForestiere parcelleforestiere, BindingResult result, RedirectAttributes redirect) {
		OlivierDemoProxy.createparcelleforestiere(parcelleforestiere);
		return new ModelAndView("redirect:myforest");
	}
	
	
	
	
	
	
	@GetMapping("login")
	public ModelAndView login() {
		return new ModelAndView("login");
	}
	
	 
	
	@RequestMapping("foo")
	public String foo() {
		throw new RuntimeException("Expected exception in controller");
	}
	
	
	
	
	
	
	
	
	
	
	
	@GetMapping(path = "dataloader")
	public ModelAndView dataloaderupload() {

		return new ModelAndView("dataloader", "test",
				storageService.loadAll()
						.map(path -> MvcUriComponentsBuilder
								.fromMethodName(OlivierDemoController.class, "serveFile", path.getFileName().toString())
								.build().toString())
						.collect(Collectors.toList()));
	}

	@GetMapping("/files/{filename:.+}")
	@ResponseBody
	public ResponseEntity<Resource> serveFile(@PathVariable String filename) {

		Resource file = storageService.loadAsResource(filename);
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
				.body(file);
	}

	@PostMapping("dataloader")
	public String handleFileUpload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {

		// BufferedReader br = null;
		// StringBuilder sb = new StringBuilder();
		try {
			Workbook workbook = new XSSFWorkbook(file.getInputStream());

			// INTEGRATION STATION FORESTIERE
			/*
			 * Sheet datatypeSheet = workbook.getSheet("stationforestiere"); Iterator<Row>
			 * iterator = datatypeSheet.iterator(); while (iterator.hasNext()) {
			 * 
			 * Row currentRow = iterator.next(); StationForestiere stationforestiere = new
			 * StationForestiere();
			 * stationforestiere.setNom(currentRow.getCell(1).getStringCellValue());
			 * stationforestiere.setDescription(currentRow.getCell(2).getStringCellValue());
			 * stationforestiere.setCaracteristique_sol(currentRow.getCell(3).
			 * getStringCellValue());
			 * stationforestiere.setPeuplement_naturel(currentRow.getCell(4).
			 * getStringCellValue()); stationforestiere =
			 * this.stationforestiereRepository.save(stationforestiere); }
			 */

			// INTEGRATION FOREST
			Sheet datatypeSheet1 = workbook.getSheet("forest");
			Iterator<Row> iterator1 = datatypeSheet1.iterator();
			iterator1.next();
			while (iterator1.hasNext()) {
				Row currentRow1 = iterator1.next();
				if (!currentRow1.getCell(1).getStringCellValue().isEmpty()) {

					Forest forest = new Forest();
					forest.setName(currentRow1.getCell(1).getStringCellValue()); // Nom
					forest.setProprietaire(currentRow1.getCell(2).getStringCellValue()); // PropriÃ©taire
					forest.setSituation_geographique(currentRow1.getCell(3).getStringCellValue()); // situation
																									// geographique
					forest.setZonage_reglementaire(currentRow1.getCell(4).getStringCellValue()); // zonage reglementaire
					forest.setDroit_usage(currentRow1.getCell(5).getStringCellValue()); // droit usage
					forest.setRegion_forestiere(currentRow1.getCell(6).getStringCellValue()); // region forestiere
					forest.setRelief(currentRow1.getCell(7).getStringCellValue()); // relief
					forest.setClimat(currentRow1.getCell(8).getStringCellValue()); // climat
					forest.setTemperature(currentRow1.getCell(9).getStringCellValue()); // temperature
					forest.setGeologie(currentRow1.getCell(10).getStringCellValue()); // geologie
					// forest.setManage_parcelle_forestiere(currentRow1.getCell(11).getBooleanCellValue());
					// //manage parcelle forestiere
					forest.setCreated_dttm(new Date());
					forest.setCreated_source("DATALOADER");
					forest.setLast_updated_dttm(new Date());
					forest.setLast_updated_source("DATALOADER");
					//forest = this.forestRepository.save(forest);
				}

			}

			// INTEGRATION PARCELLE FORESTIERE
			Sheet datatypeSheet2 = workbook.getSheet("parcelle_forestiere");
			Iterator<Row> iterator2 = datatypeSheet2.iterator();
			iterator2.next();
			while (iterator2.hasNext()) {
				Row currentRow1 = iterator2.next();
				// logger.debug(curre ntRow1.getCell(1).getStringCellValue() );
				if (!currentRow1.getCell(1).getStringCellValue().isEmpty()) {

					ParcelleForestiere parcelle_forestiere = new ParcelleForestiere();
					parcelle_forestiere.setNumero(currentRow1.getCell(1).getStringCellValue()); // NumÃ©ro
					parcelle_forestiere.setDescription(currentRow1.getCell(2).getStringCellValue());
					parcelle_forestiere.setPente(currentRow1.getCell(3).getStringCellValue());
					parcelle_forestiere.setExposition(currentRow1.getCell(4).getStringCellValue());
					parcelle_forestiere.setPosition(currentRow1.getCell(5).getStringCellValue());
					parcelle_forestiere.setRoche(currentRow1.getCell(6).getStringCellValue());
					parcelle_forestiere.setTexture(currentRow1.getCell(7).getStringCellValue());
					parcelle_forestiere.setProfondeur(currentRow1.getCell(8).getStringCellValue());
					parcelle_forestiere.setCreated_dttm(new Date());
					parcelle_forestiere.setCreated_source("DATALOADER");
					parcelle_forestiere.setLast_updated_dttm(new Date());
					parcelle_forestiere.setLast_updated_source("DATALOADER");
					//parcelle_forestiere = this.parcelleforestiereRepository.save(parcelle_forestiere);
				}
			}

			// INTEGRATION PARCELLE CADASTRALE
			Sheet datatypeSheet3 = workbook.getSheet("parcelle_cadastrale");
			Iterator<Row> iterator3 = datatypeSheet3.iterator();
			iterator3.next();
			while (iterator3.hasNext()) {
				Row currentRow1 = iterator3.next();

				// logger.debug(currentRow1.getCell(1).getStringCellValue() );
				if (!currentRow1.getCell(1).getStringCellValue().isEmpty()) {

					ParcelleCadastrale parcelle_cadastrale = new ParcelleCadastrale();
					parcelle_cadastrale.setCommune(currentRow1.getCell(1).getStringCellValue()); // Commune
					parcelle_cadastrale.setSection(currentRow1.getCell(2).getStringCellValue()); // Section
					DataFormatter formatter = new DataFormatter();
					Cell cell = currentRow1.getCell(3);
					String var_name = formatter.formatCellValue(cell);

					parcelle_cadastrale.setNumeroparcelle(var_name); // Numero
					parcelle_cadastrale.setLieu_dit(currentRow1.getCell(4).getStringCellValue()); // Lieu-dit
					parcelle_cadastrale.setSurface(currentRow1.getCell(5).getNumericCellValue()); // Surface
					parcelle_cadastrale.setCreated_dttm(new Date());
					parcelle_cadastrale.setCreated_source("DATALOADER");
					parcelle_cadastrale.setLast_updated_dttm(new Date());
					parcelle_cadastrale.setLast_updated_source("DATALOADER");
					//parcelle_cadastrale = this.parcellecadastraleRepository.save(parcelle_cadastrale);
				}
			}

			// INTEGRATION TYPE PEUPLEMENT
			Sheet datatypeSheet4 = workbook.getSheet("type_peuplement");
			Iterator<Row> iterator4 = datatypeSheet4.iterator();
			iterator4.next();
			while (iterator4.hasNext()) {
				Row currentRow1 = iterator4.next();

				// logger.debug(currentRow1.getCell(1).getStringCellValue() );
				if (!currentRow1.getCell(1).getStringCellValue().isEmpty()) {

					TypePeuplement type_peuplement = new TypePeuplement();

					type_peuplement.setNom(currentRow1.getCell(1).getStringCellValue()); // Numero
					type_peuplement.setCreated_dttm(new Date());
					type_peuplement.setCreated_source("DATALOADER");
					type_peuplement.setLast_updated_dttm(new Date());
					type_peuplement.setLast_updated_source("DATALOADER");
					//type_peuplement = this.typepeuplementRepository.save(type_peuplement);
				}
			}
			
			
			
			// INTEGRATION ESSENCE
						Sheet datatypeSheet44 = workbook.getSheet("essence");
						Iterator<Row> iterator44 = datatypeSheet44.iterator();
						iterator44.next();
						while (iterator44.hasNext()) {
							Row currentRow1 = iterator44.next();

							// logger.debug(currentRow1.getCell(1).getStringCellValue() );
							if (!currentRow1.getCell(1).getStringCellValue().isEmpty()) {

								Essence essence = new Essence();

								essence.setNom(currentRow1.getCell(1).getStringCellValue()); // Numero
								essence.setCreated_dttm(new Date());
								essence.setCreated_source("DATALOADER");
								essence.setLast_updated_dttm(new Date());
								essence.setLast_updated_source("DATALOADER");
								//essence = this.essenceRepository.save(essence);
							}
						}

						// OPERATION SYLVICOLE
						Sheet datatypeSheet65 = workbook.getSheet("operation_sylvicole");
						Iterator<Row> iterator65 = datatypeSheet65.iterator();
						iterator65.next();
						while (iterator65.hasNext()) {
							Row currentRow1 = iterator65.next();

							// logger.debug(currentRow1.getCell(1).getStringCellValue() );
							if (!currentRow1.getCell(1).getStringCellValue().isEmpty()) {

								OperationSylvicole operationsylvicole = new OperationSylvicole();

								operationsylvicole.setNom(currentRow1.getCell(1).getStringCellValue()); // Numero
								operationsylvicole.setCreated_dttm(new Date());
								operationsylvicole.setCreated_source("DATALOADER");
								operationsylvicole.setLast_updated_dttm(new Date());
								operationsylvicole.setLast_updated_source("DATALOADER");
							//	operationsylvicole = this.operationsylvicoleRepository.save(operationsylvicole);
							}
						}							
											

						// TYPE TRAVAUX
						Sheet datatypeSheet63 = workbook.getSheet("types_travaux");
						Iterator<Row> iterator63 = datatypeSheet63.iterator();
						iterator63.next();
						while (iterator63.hasNext()) {
							Row currentRow1 = iterator63.next();

							// logger.debug(currentRow1.getCell(1).getStringCellValue() );
							if (!currentRow1.getCell(1).getStringCellValue().isEmpty()) {

								TypeTravaux typetravaux = new TypeTravaux();

								typetravaux.setNom(currentRow1.getCell(1).getStringCellValue()); // Numero
								typetravaux.setCreated_dttm(new Date());
								typetravaux.setCreated_source("DATALOADER");
								typetravaux.setLast_updated_dttm(new Date());
								typetravaux.setLast_updated_source("DATALOADER");
							//	typetravaux = this.typetravauxRepository.save(typetravaux);
							}
						}							
						
			// INTEGRATION STATION
			Sheet datatypeSheet5 = workbook.getSheet("station_forestiere");
			Iterator<Row> iterator5 = datatypeSheet5.iterator();
			iterator5.next();
			while (iterator5.hasNext()) {
				Row currentRow1 = iterator5.next();

				// logger.debug(currentRow1.getCell(1).getStringCellValue() );
				if (!currentRow1.getCell(1).getStringCellValue().isEmpty()) {

					StationForestiere station_forestiere = new StationForestiere();
					station_forestiere.setNom(currentRow1.getCell(1).getStringCellValue()); // Numero
					station_forestiere.setDescription(currentRow1.getCell(2).getStringCellValue()); // Numero
					station_forestiere.setCaracteristique_sol(currentRow1.getCell(3).getStringCellValue()); // Numero
					station_forestiere.setPeuplement_naturel(currentRow1.getCell(4).getStringCellValue()); // Numero
					station_forestiere.setCreated_dttm(new Date());
					station_forestiere.setCreated_source("DATALOADER");
					station_forestiere.setLast_updated_dttm(new Date());
					station_forestiere.setLast_updated_source("DATALOADER");
					//station_forestiere = this.stationforestiereRepository.save(station_forestiere);
				}
			}
			// REPARTITION FORET

			// REPARTITION PEUPLEMENT
			Sheet datatypeSheet6 = workbook.getSheet("repartition_peuplement");
			Iterator<Row> iterator6 = datatypeSheet6.iterator();
			iterator6.next();
			while (iterator6.hasNext()) {
				Row currentRow1 = iterator6.next();

				// Foret
				DataFormatter formatter3 = new DataFormatter();
				Cell cell3 = currentRow1.getCell(0);
				String var_name3 = formatter3.formatCellValue(cell3);
				Long test3 = new Long(var_name3);

				//Optional<Forest> forest = this.forestRepository.findById(test3);

//Parcelle forestiere
				DataFormatter formatter1 = new DataFormatter();
				Cell cell = currentRow1.getCell(2);
				String var_name = formatter1.formatCellValue(cell);
				Long test = new Long(var_name);

				//Optional<ParcelleForestiere> parcelleforestieres1 = this.parcelleforestiereRepository.findById(test);

				// Parcelle cadastrale
				DataFormatter formatter2 = new DataFormatter();
				Cell cell2 = currentRow1.getCell(1);
				String var_name2 = formatter1.formatCellValue(cell2);
				Long test2 = new Long(var_name2);

				//Optional<ParcelleCadastrale> parcellecadastrales1 = this.parcellecadastraleRepository.findById(test2);

//SAVE   
				//ParcelleForestiere parcelleforestiere1 = parcelleforestieres1.get();
				//ParcelleCadastrale parcellecadastrale1 = parcellecadastrales1.get();
				// Set<ParcelleCadastrale> test123 =
				// parcelleforestieres1.get().getParcellecadastrales();
				/*Set<ParcelleCadastrale> test123 = new HashSet<ParcelleCadastrale>();

				for (Iterator<ParcelleCadastrale> it = parcelleforestieres1.get().getParcellecadastrales()
						.iterator(); it.hasNext();) {
					ParcelleCadastrale f = it.next();
					test123.add(f);

				}*/

				/*Forest forest1 = forest.get();
				test123.add(parcellecadastrales1.get());
				Set<ParcelleForestiere> test31 = new HashSet<ParcelleForestiere>();
				test31.add(parcelleforestiere1);
				for (Iterator<ParcelleForestiere> it = forest1.getParcelleforestieres().iterator(); it.hasNext();) {
					ParcelleForestiere f = it.next();
					test31.add(f);

				}*/

				// STATION FORESTIERE
				DataFormatter formatter8 = new DataFormatter();
				Cell cell8 = currentRow1.getCell(8);
				String var_name8 = formatter8.formatCellValue(cell8);
				Long test8 = new Long(var_name8);

			/*	Optional<StationForestiere> stationforestieres1 = this.stationforestiereRepository.findById(test8);

				// SAVE

				StationForestiere stationforestiere1 = stationforestieres1.get();

				Set<StationForestiere> test1238 = new HashSet<StationForestiere>();

				for (Iterator<StationForestiere> it = parcelleforestieres1.get().getStationforestieres().iterator(); it
						.hasNext();) {
					StationForestiere f = it.next();
					test1238.add(f);

				}
				test1238.add(stationforestiere1);
*/
				/*forest1.setParcelleforestieres(test31);
				forest1 = this.forestRepository.save(forest1);
				parcelleforestiere1.setParcellecadastrales(test123);
				parcelleforestiere1.setStationforestieres(test1238);
				parcelleforestiere1.setLast_updated_dttm(new Date());
				parcelleforestiere1.setLast_updated_source("DATALOADER");
				parcelleforestiere1 = this.parcelleforestiereRepository.save(parcelleforestiere1);
*/
				// PEUPLEMENT
				Peuplement peuplement = new Peuplement();
				peuplement.setCreated_source("test");
				// peuplement.setCreated_dttm(Date heure);
				peuplement.setStatus(true);
				
				// Surface
				DataFormatter formatter66 = new DataFormatter();
				Cell cell66 = currentRow1.getCell(5);
				String var_name66 = formatter66.formatCellValue(cell66);
				//Long test66 = new Long(cell66.getNumericCellValue());
				
				peuplement.setSurface(cell66.getNumericCellValue());
				peuplement.setCreated_dttm(new Date());
				peuplement.setCreated_source("ODN");
				peuplement.setLast_updated_dttm(new Date());
				peuplement.setLast_updated_source("ODN");
				//peuplement.setParcellecadastrale(parcellecadastrale1);
				peuplement.setUniteforestiere(currentRow1.getCell(3).getStringCellValue());
				peuplement.setCreated_dttm(new Date());
				peuplement.setCreated_source("DATALOADER");
				peuplement.setLast_updated_dttm(new Date());
				peuplement.setLast_updated_source("DATALOADER");
				peuplement.setDescription(currentRow1.getCell(4).getStringCellValue());
				//parcelleforestiere1 = this.parcelleforestiereRepository.save(parcelleforestiere1);

				// Type Peuplement
				DataFormatter formatter5 = new DataFormatter();
				Cell cell5 = currentRow1.getCell(6);
				String var_name5 = formatter5.formatCellValue(cell5);
				Long test5 = new Long(var_name5);
				//Optional<TypePeuplement> typepeuplemnt1 = this.typepeuplementRepository.findById(test5);

				//TypePeuplement typepeuplemnt2 = typepeuplemnt1.get();
						
				
				// Peuplement
				/*DataFormatter formatter55 = new DataFormatter();
				Cell cell55 = currentRow1.getCell(7);
				String var_name55 = formatter55.formatCellValue(cell55);
				if (!formatter55.formatCellValue(cell55).isEmpty()) {
					Long test55 = new Long(var_name55);
					Optional<Essence> essence = this.essenceRepository.findById(test55);
	
					Essence essence2 = essence.get();
					Set<Essence> test551 = new HashSet<Essence>();
					test551.add(essence.get());
	
					peuplement.setEssence(essence2);
				}
				peuplement.setTypepeuplements(typepeuplemnt2);
				peuplement.setLast_updated_dttm(new Date());
				peuplement.setLast_updated_source("DATALOADER");
				peuplement = this.peuplementRepository.save(peuplement);
*/
			}
				
				// PROGRAMMATION
				Sheet datatypeSheet88 = workbook.getSheet("programmation_sylvicole");
				Iterator<Row> iterator88 = datatypeSheet88.iterator();
				iterator88.next();
				while (iterator88.hasNext()) {
					Row currentRow88 = iterator88.next();
					DataFormatter formatter75 = new DataFormatter();
					Cell cell75 = currentRow88.getCell(1);
					String var_name75 = formatter75.formatCellValue(cell75);
					
					
					//Long test75 = new Long(var_name75);
					logger.debug("unitÃ© ");
					logger.debug(currentRow88.getCell(3).getStringCellValue());
					logger.debug("Numero de parcelle");
					logger.debug(var_name75);
					
					//ParcelleCadastrale parcellecadastrales75 = this.parcellecadastraleRepository.findByNumeroparcelle(var_name75);
					//logger.debug(parcellecadastrales75.get().);
					//logger.debug(parcellecadastrales75.getCommune());
					//ParcelleCadastrale parcellecadastrale775 = parcellecadastrales75;
					//Peuplement peuplement755 = this.peuplementRepository.findByUniteforestiereAndParcellecadastrale(currentRow88.getCell(3).getStringCellValue(),parcellecadastrale775);
					
					logger.debug("--------------------PROBLEME ORIGINAL-----------------");
					//logger.debug(parcellecadastrale775.getId().toString());
					//logger.debug(parcellecadastrale775.getNumeroparcelle());
					
					logger.debug("--------------------PROBLEME ORIGINAL2-----------------");
					logger.debug(currentRow88.getCell(3).getStringCellValue());
					logger.debug("--------------------PROBLEME ORIGINAL1-----------------");
					//logger.debug(peuplement755.getId().toString());
					
					
					if (!currentRow88.getCell(3).getStringCellValue().isEmpty()) {
					Programmation programmation = new Programmation();
					programmation.setCreated_dttm(new Date());
					programmation.setCreated_source("ODN");
					programmation.setLast_updated_dttm(new Date());
					programmation.setLast_updated_source("ODN");
					
					
					programmation.setPrevision(currentRow88.getCell(4).getDateCellValue());
					programmation.setStatus(false);
					programmation.setDescription(currentRow88.getCell(5).getStringCellValue());
					programmation.setType(currentRow88.getCell(6).getStringCellValue());
					programmation.setCreated_dttm(new Date());
					programmation.setCreated_source("DATALOADER");
					programmation.setLast_updated_dttm(new Date());
					programmation.setLast_updated_source("DATALOADER");
				//	programmation = this.programmationRepository.save(programmation);
					
					
					Set<Programmation> test92 = new HashSet<Programmation>();
				/*	for (Iterator<Programmation> it = peuplement755.getProgrammation().iterator(); it.hasNext();) {
						Programmation f = it.next();
						test92.add(f);
					}*/
					test92.add(programmation);
					/*Peuplement peuplement700 = new Peuplement();
					peuplement700 = peuplement755;*/
					logger.debug("--------------------PROBLEME-----------------");
					logger.debug(programmation.getId().toString());
					logger.debug("--------------------PROBLEME    1-----------------");
					//logger.debug(peuplement755.getId().toString());
					
					
					//peuplement755.setProgrammation(test92);
					//peuplement755.setLast_updated_dttm(new Date());
					//peuplement755.setLast_updated_source("DATALOADER");
					//peuplement755 = this.peuplementRepository.save(peuplement755);
					}
			}

		} catch (IOException e1) {
			
			e1.printStackTrace();
		}

		storageService.store(file);
		redirectAttributes.addFlashAttribute("message",
				"You successfully uploaded " + file.getOriginalFilename() + "!");

		return "redirect:/forestlist";
	}
	
	
	
	
	
	
	
	
	
}
