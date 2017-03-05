package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ConfigurationRepository;
import domain.Configuration;
import forms.ConfigurationForm;
@Service
@Transactional
public class ConfigurationService {

	//Managed Repository --------------------------------------
		@Autowired
		private ConfigurationRepository configurationRepository;
		
	//Supporting Services --------------------------------------
	//Simple CRUD methods --------------------------------------
		public Configuration create() {
			Configuration result;
			result = new Configuration();
			return result;
		}
		public Collection<Configuration> findAll() {
			Collection<Configuration> result;
			Assert.notNull(configurationRepository);
			result = configurationRepository.findAll();		
			Assert.notNull(result);
			return result;
		}

		public Configuration findOne() {
			Configuration result=null;
			Collection<Configuration> configurations = configurationRepository.findAll();
			for(Configuration configuration:configurations){
				result=configuration;
				break;
			}
			return result;
		}
		
		public Configuration save(Configuration configuration) {
			Assert.notNull(configuration);
			Configuration result;
			Assert.notNull(configuration.getBannerCost());
			Assert.isTrue(configuration.getBannerCost()>=0);
			result = configurationRepository.save(configuration);
			return result;
		}	
		
	//other business methods --------------------------------------
		public Configuration setConfiguration(ConfigurationForm configurationForm){
			Configuration configuration;
			configuration = findOne();
			List<String> keyWords = new ArrayList<String>();
			keyWords.addAll(configuration.getKeyWords());
			keyWords.add(configurationForm.getText());
			configuration.setKeyWords(keyWords);
			Configuration savedConfiguration=save(configuration);
			return savedConfiguration;
		}
		public Configuration deleteKeyWord(Integer keyWordIndex) {
			Configuration result;
			Configuration configuration=findOne();
			List<String> keyWords = new ArrayList<String>();
			keyWords.addAll(configuration.getKeyWords());
			String keyWordToDelete= keyWords.get(Integer.valueOf(keyWordIndex));
			keyWords.remove(keyWordToDelete);
			configuration.setKeyWords(keyWords);
			result=save(configuration);
			return result;
		}
}
