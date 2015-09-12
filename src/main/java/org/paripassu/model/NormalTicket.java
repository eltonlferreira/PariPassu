package org.paripassu.model;

import org.paripassu.model.type.TicketType;

public class NormalTicket extends AbstractTicket {

	public NormalTicket(Integer key) {
		super(key, TicketType.NORMAL);
	}
}
