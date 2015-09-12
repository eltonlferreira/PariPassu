package org.paripassu.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.paripassu.model.type.TicketType;

@XmlRootElement(name = "ticket")
public abstract class AbstractTicket implements Comparable<AbstractTicket> {

	private Integer key;
	private TicketType type;

	public AbstractTicket(Integer key, TicketType type) {
		this.key = key;
		this.type = type;
	}

	@XmlTransient
	public Integer getKey() {
		return key;
	}

	@XmlTransient
	public TicketType getType() {
		return type;
	}

	@XmlElement(name = "value", required = true)
	public String getPass() {
		return String.format("%s%04d", this.getType().getSigla(), this.key);
	}

	@Override
	public String toString() {
		return getPass();
	}

	@Override
	public int compareTo(AbstractTicket another) {
		int diff = this.getType().getPriority().compareTo(another.getType().getPriority());
		if (diff != 0) {
			return diff;
		}

		diff = this.getKey().compareTo(another.getKey());
		return diff;
	}

}
