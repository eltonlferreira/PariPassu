package org.paripassu.service.impl;

import java.util.Collection;
import java.util.Queue;
import java.util.Random;
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
		preferCounter.set(start);
		normalCounter.set(start);
	}

	@Override
	public AbstractTicket nextTicket() {
		return tickets.size() == 1 ? tickets.peek() : tickets.poll();

	}

	// Metodo simples para gerar senhas
	private AbstractTicket makeNormalTicket() {
		return new NormalTicket(normalCounter.incrementAndGet());
	}

	private AbstractTicket makePreferencialTicket() {
		return new PriorityTicket(preferCounter.incrementAndGet());
	}

	public static void main(String[] args) {
		TicketServiceImpl service = new TicketServiceImpl();

		Random rand = new Random();
		for (int i = 0; i < 1000; i++) {
			if (rand.nextInt(4) != 3) {
				service.takeNormal();
			} else {
				service.takePreferencial();
			}
		}

		for (int i = 0; i < 1000; i++) {
			System.out.printf("Ticket %s\n", service.nextTicket());
		}

		for (int i = 0; i < 10; i++) {
			System.out.printf("Ticket %s\n", service.nextTicket());
		}
	}

}
