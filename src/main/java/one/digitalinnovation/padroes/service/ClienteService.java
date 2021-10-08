package one.digitalinnovation.padroes.service;

import one.digitalinnovation.padroes.model.Cliente;

/**
 * Interface que define o padrão Strategy no domínio de cliente. 
 * 
 * @author Felipe Gustavo
 */
public interface ClienteService {

	Iterable<Cliente> buscarTodos();

	Cliente buscarPorId(Long id);

	void inserir(Cliente cliente);

	void atualizar(Long id, Cliente cliente);

	void deletar(Long id);

}
