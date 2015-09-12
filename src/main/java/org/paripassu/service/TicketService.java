package org.paripassu.service;

import java.util.Collection;

import org.paripassu.model.AbstractTicket;

public interface TicketService {

	// Admin
	AbstractTicket nextTicket();

	void resetTicketSequence();

	void resetTicketSequence(Integer start);

	// normal
	Collection<AbstractTicket> viewAllTickets();

	AbstractTicket takeNormal();

	AbstractTicket takePreferencial();

	AbstractTicket viewLast();

}
