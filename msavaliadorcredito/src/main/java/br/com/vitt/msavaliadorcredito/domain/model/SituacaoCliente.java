package br.com.vitt.msavaliadorcredito.domain.model;

import java.util.List;

import lombok.Data;

@Data
public class SituacaoCliente {

	private DadosCliente cliente;
	private List<CartaoCliente> cartoes;
}
