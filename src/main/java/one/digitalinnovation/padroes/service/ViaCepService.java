package one.digitalinnovation.padroes.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import one.digitalinnovation.padroes.model.Endereco;

/**
 * Client HTTP, criado via OpenFeign para o consumo da API do
 * ViaCEP.
 * 
 * 
 * @author Felipe Gustavo
 */
@FeignClient(name = "viacep", url = "https://viacep.com.br/ws")
public interface ViaCepService {

	@GetMapping("/{cep}/json/")
	Endereco consultarCep(@PathVariable("cep") String cep);
}
