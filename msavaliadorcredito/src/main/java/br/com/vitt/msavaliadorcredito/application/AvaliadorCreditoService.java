package br.com.vitt.msavaliadorcredito.application;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.vitt.msavaliadorcredito.application.ex.DadosClienteNotFoundException;
import br.com.vitt.msavaliadorcredito.application.ex.ErroComunicacaoMicroservicesException;
import br.com.vitt.msavaliadorcredito.domain.model.CartaoCliente;
import br.com.vitt.msavaliadorcredito.domain.model.DadosCliente;
import br.com.vitt.msavaliadorcredito.domain.model.SituacaoCliente;
import br.com.vitt.msavaliadorcredito.infra.clients.CartoesResourceClient;
import br.com.vitt.msavaliadorcredito.infra.clients.ClienteResourceClient;
import feign.FeignException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AvaliadorCreditoService {
	
	private final ClienteResourceClient clienteResourceClient;
	
	private final CartoesResourceClient cartoesResourceClient;

	public SituacaoCliente obterSituacaoCliente(String cpf) throws DadosClienteNotFoundException, ErroComunicacaoMicroservicesException{
		
		try {
		
		ResponseEntity<DadosCliente> dadosClienteResponse = clienteResourceClient.dadosCliente(cpf);
		ResponseEntity<List<CartaoCliente>> cartoesResponse = cartoesResourceClient.getCartoesByCliente(cpf);
		
		return SituacaoCliente
				.builder()
				.cliente(dadosClienteResponse.getBody())
				.cartoes(cartoesResponse.getBody())
				.build();
		}catch(FeignException.FeignClientException e) {
			
			int status = e.status();
			
			if(HttpStatus.NOT_FOUND.value() == status) {
				throw new DadosClienteNotFoundException();
			}
			
			throw new ErroComunicacaoMicroservicesException(e.getMessage(), status);
		}
		
	}
}
