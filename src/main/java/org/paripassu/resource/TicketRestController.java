package org.paripassu.resource;

import java.io.Serializable;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.paripassu.model.AbstractTicket;
import org.paripassu.service.TicketService;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;

@Path("/ticket")
@Api(value = "/ticket", description = "Rest api para operações com o sistema de ticket", produces = MediaType.APPLICATION_JSON)
@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
@Lock(LockType.READ)
public class TicketRestController implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private transient Logger log;

	@Inject
	private TicketService ticketService;

	public TicketRestController() {
	}

	@GET
	@Path("/all")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Função pública, Devolva uma lista com todos os tickets em fila", httpMethod = "GET", notes = "Devolve a lista contendo todos os tickets", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Devolve uma lista de tickets") })
	public Response viewAllTickets() {
		Collection<AbstractTicket> tickets = ticketService.viewAllTickets();
		log.log(Level.INFO, "Lista de Tickets devolvida com {0} tickets.", new Object[] { tickets.size() });
		return Response.ok().entity(tickets).build();
	}

	@GET
	@Path("/normal")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Função pública, Pega um novo ticket NORMAL e o adiciona na fila", httpMethod = "GET", notes = "Devolve o valor do novo Ticket normal", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Devolve um ticket NORMAL com o formato NXXXX") })

	@Lock(LockType.WRITE)
	public Response takeNormalTicket() {
		AbstractTicket ticket = ticketService.takeNormal();
		log.log(Level.INFO, "Ticket normal {0} gerado.", new Object[] { ticket });
		return Response.ok().entity(ticket).build();
	}

	@GET
	@Path("/priority")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Função pública, Pega um novo ticket PREFERENCIAL e o adiciona na fila", httpMethod = "GET", notes = "Devolve o valor do novo Ticket preferencial", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Devolve um ticket PREFERENCIAL com o formato PXXXX") })

	@Lock(LockType.WRITE)
	public Response takePriorityTicket() {
		AbstractTicket ticket = ticketService.takePreferencial();
		log.log(Level.INFO, "Ticket preferencial {0} gerado.", new Object[] { ticket });
		return Response.ok().entity(ticket).build();
	}

	@GET
	@Path("/last")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Função pública, devolve o ultimo Ticket da fila mas sem remove-lo da fila", httpMethod = "GET", notes = "O ultimo valor para fins de consulta, operação não altera a fila", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Devolve um ticket NORMAL com o formato NXXXX"),
			@ApiResponse(code = 204, message = "Devolve um erro por falta de conteúdo na fila, deve ser executado primeiro o takeNormalTicket() ou takePriorityTicket().") })
	public Response lastOne() {
		AbstractTicket ticket = ticketService.viewLast();
		if (ticket != null) {
			log.log(Level.INFO, "Ticket normal {0} visualizado.", new Object[] { ticket });
			return Response.ok().entity(ticket).build();
		} else {
			log.log(Level.WARNING, "A fila está vazia, execute primeiro o TAKE.");
			return Response.status(Response.Status.NO_CONTENT).build();
		}
	}

	@GET
	@Path("/admin/next")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Função administrativa, chama o próximo Ticket da fila e também remove o mesmo da fila", httpMethod = "GET", notes = "O ultimo valor para fins de consumo da fila, operação altera a fila", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Devolve um ticket NORMAL com o formato NXXXX"),
			@ApiResponse(code = 204, message = "Devolve um erro por falta de conteúdo na fila, deve ser executado primeiro o takeNormalTicket() ou takePriorityTicket().") })

	@Lock(LockType.WRITE)
	public Response nextTicket() {
		AbstractTicket ticket = ticketService.nextTicket();
		if (ticket != null) {
			log.log(Level.INFO, "Ticket {0} foi chamado.", new Object[] { ticket });
			return Response.ok().entity(ticket).build();
		} else {
			log.log(Level.WARNING, "A fila está vazia, execute primeiro o TAKE.");
			return Response.status(Response.Status.NO_CONTENT).build();
		}
	}

	@GET
	@Path("/admin/reset")
	@ApiOperation(value = "Função administrativa, reinicia a numeração da fila de tickets para o valor inicial.", httpMethod = "GET", notes = "Sempre reinicial com o valor 1.", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Devolve um ticket NORMAL com o formato NXXXX") })

	@Lock(LockType.WRITE)
	public Response resetTicket() {
		log.log(Level.INFO, "Tickets reiniciados para o valor default igual a zero.");
		ticketService.resetTicketSequence();
		return Response.ok().build();
	}

	@GET
	@Path("/admin/reset/{sequence}")
	@ApiOperation(value = "Função administrativa, reinicia a numeração da fila de tickets para o valor inteiro informado.", httpMethod = "GET", notes = "Sempre reinicial com o valor 1.", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Devolve um ticket NORMAL com o formato NXXXX") })

	@Lock(LockType.WRITE)
	public Response resetTicket(
			@ApiParam(value = "Inteiro positivo representa a nova sequencia.", required = true) @PathParam("sequence") Integer sequence) {
		if (sequence != null && sequence > 0) {
			log.log(Level.INFO, "Tickets reiniciados em: {0}", new Object[] { sequence });
			ticketService.resetTicketSequence(sequence);
			return Response.ok().build();
		}
		log.log(Level.WARNING, "Valor nulo ou menor que 0, foi passado para reiniciar a sequencia dos ticket.");
		return Response.status(Response.Status.PRECONDITION_FAILED).build();
	}

}
