package br.com.vitt.mscartoes.application;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.vitt.mscartoes.domain.ClienteCartao;
import br.com.vitt.mscartoes.infra.repository.ClienteCartaoRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClienteCartaoService {

	private ClienteCartaoRepository repository;
	
	public List<ClienteCartao> listCartoesByCpf(String cpf){
		return repository.findByCpf(cpf);
	}
	
}
