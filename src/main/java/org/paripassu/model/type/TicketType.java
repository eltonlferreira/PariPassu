package org.paripassu.model.type;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "TicketType")
@XmlEnum
public enum TicketType {

	PRIORITY("P", 0), NORMAL("N", 1);
	private String sigla;
	private Integer priority;

	private TicketType(String sigla, Integer priority) {
		this.sigla = sigla;
		this.priority = priority;
	}

	public String getSigla() {
		return sigla;
	}

	public Integer getPriority() {
		return priority;
	}

}
