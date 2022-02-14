package by.issoft.domain.train;

import by.issoft.domain.cargo.Cargo;
import by.issoft.domain.carriages.CargoCarriage;
import by.issoft.domain.carriages.Carriage;
import by.issoft.domain.carriages.Locomotive;
import by.issoft.domain.carriages.PassengerCarriage;
import by.issoft.domain.users.Passenger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import static com.google.inject.internal.util.Preconditions.*;

public class Train {
    private static final Logger logger = LoggerFactory.getLogger(Train.class);
    private final String number;
    private Node<Carriage> head;
    private Node<Carriage> tail;

    public Train(String number) {
        logger.debug("creating train " + number);
        checkNotNull(number, "number should not be null");
        checkArgument(!"".equals(number), "number should contain some text");
        this.number = number;
    }

    public void addCarriage(Carriage carriage) {
        logger.debug("adding carriage to train " + number);
        checkNotNull(carriage, "cant add null carriage");
        if (head == null) {
            head = new Node<>(carriage);
            tail = head;
        } else {
            Node<Carriage> newCarriage = new Node<>(carriage);
            tail.setNext(newCarriage);
            tail = newCarriage;
        }
    }

    public void addCarriage(List<Carriage> carriageList) {
        carriageList.forEach(this::addCarriage);
    }

    public boolean validate() {
        logger.debug("validate train " + number);
        checkNotNull(head, "train should have at least locomotive in head or tail");
        checkState(head.getValue() instanceof Locomotive || tail.getValue() instanceof Locomotive,
                "train must have locomotive carriage in head or tail");
        Node<Carriage> tempCarriageNode = head.getNext();
        while (tempCarriageNode != tail) {
            checkState(!(tempCarriageNode.getValue() instanceof Locomotive),
                    "train cant contain locomotive in the middle");
            tempCarriageNode = tempCarriageNode.getNext();
        }
        return true;
    }

    public boolean addPassenger(Passenger passenger) {
        logger.debug("adding passenger " + passenger.getFullName() + " to train " + number);
        Node<Carriage> tempCarriageNode = head;
        boolean placed = false;
        while (!placed && tempCarriageNode != null) {
            if (tempCarriageNode.getValue() instanceof PassengerCarriage passengerCarriage) {
                try {
                    passengerCarriage.addPassenger(passenger);
                    placed = true;
                } catch (IllegalStateException e) {
                    tempCarriageNode = tempCarriageNode.getNext();
                }
            } else {
                tempCarriageNode = tempCarriageNode.getNext();
            }
        }
        if (!placed) {
            throw new IllegalStateException("cant place the cargo " + passenger.getFullName());
        }
        return true;
    }

    public boolean addPassenger(List<Passenger> passengerList) {
        Node<Carriage> tempCarriageNode = head;
        for (Passenger passenger : passengerList) {
            addPassenger(passenger);
        }
        return true;
    }

    public boolean addCargo(Cargo cargo) {
        logger.debug("adding cargo " + cargo.getId() + " to train " + number);
        Node<Carriage> tempCarriageNode = head;
        boolean placed = false;
        while (!placed && tempCarriageNode != null) {
            if (tempCarriageNode.getValue() instanceof CargoCarriage cargoCarriage) {
                try {
                    cargoCarriage.addCargo(cargo);
                    placed = true;
                } catch (IllegalStateException e) {
                    tempCarriageNode = tempCarriageNode.getNext();
                }
            } else {
                tempCarriageNode = tempCarriageNode.getNext();
            }
        }
        if (!placed) {
            throw new IllegalStateException("cant place the cargo " + cargo.getId());
        }
        return true;
    }

    public boolean addCargo(List<Cargo> cargoList) {
        Node<Carriage> tempCarriageNode = head;
        for (Cargo cargo : cargoList) {
            addCargo(cargo);
        }
        return true;
    }

    public List<Carriage> getListOfCarriages() {
        List<Carriage> carriageList = new ArrayList<>();
        Node<Carriage> tempCarriageNode = head;
        while (tempCarriageNode != null) {
            carriageList.add(tempCarriageNode.getValue());
            tempCarriageNode = tempCarriageNode.getNext();
        }
        return carriageList;
    }
}


class Node<E> {
    private E value;
    private Node<E> next;

    public Node(E value) {
        this.value = value;
    }

    public E getValue() {
        return value;
    }

    public Node<E> getNext() {
        return next;
    }

    public void setNext(Node<E> next) {
        this.next = next;
    }
}
