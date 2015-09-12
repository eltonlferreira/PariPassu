package org.paripassu.model;

import org.paripassu.model.type.TicketType;

public class PriorityTicket extends AbstractTicket {

	public PriorityTicket(Integer key) {
		super(key, TicketType.PRIORITY);
	}

}
