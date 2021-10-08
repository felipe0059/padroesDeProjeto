package one.digitalinnovation.padroes.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import one.digitalinnovation.padroes.model.Cliente;
import one.digitalinnovation.padroes.model.ClienteRepository;
import one.digitalinnovation.padroes.model.Endereco;
import one.digitalinnovation.padroes.model.EnderecoRepository;
import one.digitalinnovation.padroes.service.ClienteService;
import one.digitalinnovation.padroes.service.ViaCepService;

/**
 * Implementação da Strategy {@link ClienteService}. Com isso, como essa classe é um
 * {@link Service} e ela será tratada como um Singleton.
 * 
 * @author Felipe Gustavo
 */
@Service
public class ClienteServiceImpl implements ClienteService {

	// Singleton: Injeta os componentes do Spring com @Autowired.
	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private EnderecoRepository enderecoRepository;
	@Autowired
	private ViaCepService viaCepService;
	
	// Strategy: Implementa os métodos definidos na interface.
	

	@Override
	public Iterable<Cliente> buscarTodos() {
		// Buscar todos os clientes.
		return clienteRepository.findAll();
	}

	@Override
	public Cliente buscarPorId(Long id) {
		// Buscar cliente por ID.
		Optional<Cliente> cliente = clienteRepository.findById(id);
		return cliente.get();
	}

	@Override
	public void inserir(Cliente cliente) {
		salvarClienteComCep(cliente);
	}

	@Override
	public void atualizar(Long id, Cliente cliente) {
		// Busca cliente por ID, caso exista:
		Optional<Cliente> clienteBd = clienteRepository.findById(id);
		if (clienteBd.isPresent()) {
			salvarClienteComCep(cliente);
		}
	}

	@Override
	public void deletar(Long id) {
		// Deleta cliente por ID.
		clienteRepository.deleteById(id);
	}

	private void salvarClienteComCep(Cliente cliente) {
		// Verificar se o endereco do cliente já existe (pelo CEP).
		String cep = cliente.getEndereco().getCep();
		Endereco endereco = enderecoRepository.findById(cep).orElseGet(() -> {
			// Caso negativo, integra com o ViaCEP e persiste no retorno.
			Endereco novoEndereco = viaCepService.consultarCep(cep);
			enderecoRepository.save(novoEndereco);
			return novoEndereco;
		});
		cliente.setEndereco(endereco);
		// Insere cliente e vincula o endereco.
		clienteRepository.save(cliente);
	}

}
