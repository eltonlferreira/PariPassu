package org.paripassu.service.impl;

import java.util.Collection;
import java.util.Queue;
import java.util.TreeSet;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

import org.paripassu.model.AbstractTicket;
import org.paripassu.model.NormalTicket;
import org.paripassu.model.PriorityTicket;
import org.paripassu.service.TicketService;

@Named
@ApplicationScoped
public class TicketServiceImpl implements TicketService {
	private final int FINAL = 10000;
	// garantir numeros unicos
	private AtomicInteger preferCounter = new AtomicInteger();
	private AtomicInteger normalCounter = new AtomicInteger();

	private Queue<AbstractTicket> tickets = new PriorityBlockingQueue<AbstractTicket>(30);

	@Override
	public Collection<AbstractTicket> viewAllTickets() {
		return new TreeSet<AbstractTicket>(tickets);
	}

	@Override
	public AbstractTicket takeNormal() {
		AbstractTicket toReturn = makeNormalTicket();
		tickets.add(toReturn);
		return toReturn;
	}

	@Override
	public AbstractTicket takePreferencial() {
		AbstractTicket toReturn = makePreferencialTicket();
		tickets.add(toReturn);
		return toReturn;
	}

	@Override
	public AbstractTicket viewLast() {
		return tickets.peek();
	}

	@Override
	public void resetTicketSequence() {
		preferCounter.set(0);
		normalCounter.set(0);
	}

	@Override
	public void resetTicketSequence(Integer start) {
		if (start > 0) {
			preferCounter.set(start % FINAL == 0 ? 1 : start);
			normalCounter.set(start % FINAL == 0 ? 1 : start);
		}
	}

	@Override
	public AbstractTicket nextTicket() {
		return tickets.size() == 1 ? tickets.peek() : tickets.poll();

	}

	// Metodo simples para gerar senhas
	private AbstractTicket makeNormalTicket() {
		int pos = normalCounter.incrementAndGet();
		if (pos % FINAL == 0) {
			normalCounter.set(0);
			return new NormalTicket(normalCounter.incrementAndGet());
		}
		return new NormalTicket(pos);
	}

	private AbstractTicket makePreferencialTicket() {
		int pos = preferCounter.incrementAndGet();
		if (pos % FINAL == 0) {
			preferCounter.set(0);
			return new PriorityTicket(preferCounter.incrementAndGet());
		}
		return new PriorityTicket(pos);
	}

}
