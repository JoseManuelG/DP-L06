package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.SponsorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Attend;
import domain.Bill;
import domain.Campaign;
import domain.CreditCard;
import domain.Folder;
import domain.Message;
import domain.SocialIdentity;
import domain.Sponsor;
import forms.ActorForm;

@Service
@Transactional
public class SponsorService {
	//Manage Repository-------------------------------
	
	@Autowired
	private SponsorRepository sponsorRepository;
		
	//Supporting Services-----------------------------
	@Autowired
	private FolderService folderService;
	
	@Autowired
	private BillService billService;
	
	@Autowired
	private MessageService messageService;
	
	
	@Autowired
	private LoginService loginService;
	//Constructors------------------------------------
	
	//Simple CRUD methods-----------------------------
	public Sponsor create(){
		Sponsor result=new Sponsor();
		Collection<CreditCard> creditCards=new ArrayList<CreditCard>();
		result.setCreditCards(creditCards);result.setAttends(new ArrayList<Attend>());
		result.setFolders(new ArrayList<Folder>());
		result.setAttends(new ArrayList<Attend>());
		result.setSocialIdentities(new ArrayList<SocialIdentity>());
		result.setBills(new ArrayList<Bill>());
		result.setCampaigns(new ArrayList<Campaign>());

		return result;
	}
	
	public Sponsor save(Sponsor sponsor){
		Assert.notNull(sponsor,"SAVE: El sponsor no puede ser null");
		Sponsor result=sponsorRepository.save(sponsor);
		return result;
	}
	
	public Collection<Sponsor> findAll(){
		Collection<Sponsor> result=sponsorRepository.findAll();
		return result;
	}
	
	public Sponsor findOne(Integer sponsorId){
		Sponsor result=sponsorRepository.findOne(sponsorId);
		return result;
	}

	@SuppressWarnings("static-access")
	public void delete(Sponsor sponsor){
		UserAccount principal=loginService.getPrincipal();
		Assert.notNull(sponsor,"DELETE: El sponsor no puede ser null");
		Assert.isTrue(sponsor.getId()!=0,"El sponsor ha de haber sido guardado");
		Assert.isTrue(principal.equals(sponsor.getUserAccount()),"UserAccount no valido");
		sponsorRepository.delete(sponsor);
	}
	
	//Other business methods--------------------------
	
	public Sponsor findByPrincipal() {
		Sponsor result;
		UserAccount userAccount;
	
		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		result = sponsorRepository.findBySponsorAccountId(userAccount.getId());
		Assert.notNull(result);

		return result;
	}
	
	public Integer minOfCampaignperSponsor(){
		Integer result=sponsorRepository.minOfCampaignperSponsor();
		return result;
	}
	
	public Double avgOfCampaignperSponsor(){
		Double result=sponsorRepository.avgOfCampaignperSponsor();
		return result;
	}
	
	public Integer maxOfCampaignperSponsor(){
		Integer result=sponsorRepository.maxOfCampaignperSponsor();
		return result;
	}
	
	public Integer minOfActiveCampaignperSponsor(){
		Collection<Sponsor> sponsors=sponsorRepository.findAll();
		Integer result=0,iteracion=0;
		Integer ActiveCampaigns=0;
		Date currentDate=new Date(System.currentTimeMillis());
		for(Sponsor sponsor:sponsors){
			ActiveCampaigns=0;iteracion++;
			for(Campaign campaign:sponsor.getCampaigns()){
				if(campaign.getDateOfStart().before(currentDate)&&campaign.getDateOfEnd().after(currentDate)){
					ActiveCampaigns++;
				}
			}
			if(result>ActiveCampaigns||iteracion==1){
				result=ActiveCampaigns;
			}
		}
		return result;
	}
	
	public Double avgOfActiveCampaignperSponsor(){
		Collection<Sponsor> sponsors=sponsorRepository.findAll();
		Double result=0.0;
		Integer iteracion=0;
		Integer ActiveCampaigns=0;
		Date currentDate=new Date(System.currentTimeMillis());
		for(Sponsor sponsor:sponsors){
			ActiveCampaigns=0;iteracion++;
			for(Campaign campaign:sponsor.getCampaigns()){
				if(campaign.getDateOfStart().before(currentDate)&&campaign.getDateOfEnd().after(currentDate)){
					ActiveCampaigns++;
				}
			}
			result+=ActiveCampaigns;
			}
		result=result/sponsors.size();
		return result;
	}
	
	public Integer maxOfActiveCampaignperSponsor(){
		Collection<Sponsor> sponsors=sponsorRepository.findAll();
		Integer result=0,iteracion=0;
		Integer ActiveCampaigns=0;
		Date currentDate=new Date(System.currentTimeMillis());
		for(Sponsor sponsor:sponsors){
			ActiveCampaigns=0;iteracion++;
			for(Campaign campaign:sponsor.getCampaigns()){
				if(campaign.getDateOfStart().before(currentDate)&&campaign.getDateOfEnd().after(currentDate)){
					ActiveCampaigns++;
				}
			}
			if(result<ActiveCampaigns){
				result=ActiveCampaigns;
			}
		}
		return result;
	}
	
	public Collection<String> listOfCompaniesByCampaignsOrganiced(){
		Collection<String> result= sponsorRepository.listOfCompaniesByCampaignsOrganiced();
		return result;
	}
	
	public Collection<String> listOfCompaniesByMonthlyBills(){
		Collection<String> result= sponsorRepository.listOfCompaniesByMonthlyBills();
		return result;
	}
	
	public Double avgOfPaidBills(){
		Double result=sponsorRepository.avgOfPaidBills();
		return result;
	}
	
	public Double sttdevOfPaidBills(){
		Double result=sponsorRepository.sttdevOfPaidBills();
		return result;
	}
	
	public Double avgOfUnpaidBills(){
		Double result=sponsorRepository.avgOfUnpaidBills();
		return result;
	}
	
	public Double sttdevOfUnpaidBills(){
		Double result=sponsorRepository.sttdevOfUnpaidBills();
		return result;
	}
	
	public Collection<Sponsor> sponsorsWithNoCampaignOnThreeMonths(){
		Collection<Sponsor> result=sponsorRepository.sponsorsWithNoCampaignOnThreeMonths();
		return result;
	}
	
	public Collection<String> listOfCompaniesThatSpentLessThanTheAvg(){
		Collection<String> result= sponsorRepository.listOfCompaniesThatSpentLessThanTheAvg();
		return result;
	}
	
	public Collection<String> listOfCompaniesThatSpentMoreThan90(){
		Collection<String> result=sponsorRepository.listOfCompaniesThatSpentMoreThan90();
		return result;
	}
	@SuppressWarnings(value = { "deprecation" })
	public void sendMessageToSponsorsWithUnpaidBills(){
		Collection<Bill> bills=billService.UnpaidBills();
		Date currentDate=new Date(System.currentTimeMillis());
		Message message=new Message();
		Folder folder=new Folder();
		for(Bill bill:bills){
			if((bill.getDateOfCreation().getMonth()-currentDate.getMonth())<0&&(bill.getDateOfCreation().getYear()-currentDate.getYear())<=0){
				folder=folderService.findFolderOfActor(bill.getSponsor(), "inbox");
				message=messageService.create();
				message.setFolder(folder);
				message.setSubject("Unpaind Monthly Bills");
				message.setBody("Bills must be paid 30 days after creation");
				message.setRecipient(bill.getSponsor().getName());
				message.setSender("System");
				message.setPriority("HIGH");
				messageService.save(message);
			}
		}
	}

	public Sponsor sponsorWithCreditCard(CreditCard creditCard) {
		Collection<Sponsor> sponsors=sponsorRepository.findAll();
		Sponsor result=null;
		for(Sponsor sponsor:sponsors){
			if(sponsor.getCreditCards().contains(creditCard)){
				result=sponsor;
				break;
			}
		}
		return result;
	}
	public Collection<Sponsor> searchForSponsor(String searchTerm){
		Collection<Sponsor> result = sponsorRepository.searchForSponsor(searchTerm);
		return result;
	}
		public void newSponsor(ActorForm actorForm){
		Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		UserAccount userAccount = new UserAccount();
		userAccount.setPassword(encoder.encodePassword(actorForm.getPassword(), null));
		userAccount.setUsername(actorForm.getUsername());
		
		Sponsor sponsor =create();
		
		sponsor.setName(actorForm.getName());
		sponsor.setSurname(actorForm.getSurname());
		sponsor.setAddress(actorForm.getAddress());
		sponsor.setEmail(actorForm.getEmail());
		sponsor.setPhone(actorForm.getPhone());
		sponsor.setCompanyName(actorForm.getCompanyName());
		
		Collection<Authority> authorities = new ArrayList<Authority>();
		Authority authority = new Authority();
		authority.setAuthority(actorForm.getTypeOfActor());
		authorities.add(authority);
		userAccount.setAuthorities(authorities);
		sponsor.setUserAccount(userAccount);
		folderService.createBasicsFolders(sponsor);
		save(sponsor);
	}
}
