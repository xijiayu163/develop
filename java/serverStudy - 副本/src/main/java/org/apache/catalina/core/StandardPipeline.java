package org.apache.catalina.core;

import java.util.ArrayList;

import org.apache.catalina.Contained;
import org.apache.catalina.Container;
import org.apache.catalina.Lifecycle;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.LifecycleState;
import org.apache.catalina.Pipeline;
import org.apache.catalina.Valve;
import org.apache.catalina.util.LifecycleBase;

public class StandardPipeline extends LifecycleBase implements Pipeline,Contained{
	
	protected Container container = null;
	protected Valve basic = null;
	protected Valve first = null;
	
	public StandardPipeline(){
		this(null);
	}
	
	public StandardPipeline(Container container) {
		setContainer(container);
	}


	public Valve getBasic() {
		return (this.basic);
	}

	public void setBasic(Valve valve) {
		this.basic = valve;
	}
	
	/**
	 * 链表结构作插入
	 */
	public void addValve(Valve valve) {
		if (valve instanceof Contained)
			((Contained) valve).setContainer(this.container);
		
		if (getState().isAvailable()) {
            if (valve instanceof Lifecycle) {
                try {
                    ((Lifecycle) valve).start();
                } catch (LifecycleException e) {
                }
            }
        }
		
		if (first == null) {
            first = valve;
            valve.setNext(basic);
        } else {
            Valve current = first;
            while (current != null) {
                if (current.getNext() == basic) {
                    current.setNext(valve);
                    valve.setNext(basic);
                    break;
                }
                current = current.getNext();
            }
        }
	}

	public Valve[] getValves() {
		ArrayList<Valve> valveList = new ArrayList<Valve>();
		Valve current = first;
		if (current == null) {
            current = basic;
        }
		while (current != null) {
            valveList.add(current);
            current = current.getNext();
        }
		return valveList.toArray(new Valve[0]);
	}
	
	/**
	 * 链表结构做删除
	 */
	public void removeValve(Valve valve) {
		Valve current;
        if(first == valve) {
            first = first.getNext();
            current = null;
        } else {
            current = first;
        }
        while (current != null) {
            if (current.getNext() == valve) {
                current.setNext(valve.getNext());
                break;
            }
            current = current.getNext();
        }
        
        if (first == basic) first = null;
        
        if (valve instanceof Contained)
            ((Contained) valve).setContainer(null);
        
        if (valve instanceof Lifecycle) {
            // Stop this valve if necessary
            if (getState().isAvailable()) {
                try {
                    ((Lifecycle) valve).stop();
                } catch (LifecycleException e) {
                }
            }
            try {
                ((Lifecycle) valve).destroy();
            } catch (LifecycleException e) {
            }
        }
        
        container.fireContainerEvent(Container.REMOVE_VALVE_EVENT, valve);
        
	}

	public Valve getFirst() {
		if (first != null) {
            return first;
        }
        
        return basic;
	}

	public Container getContainer() {
		return this.container;
	}

	public void setContainer(Container container) {
		this.container = container;
	}

	@Override
	protected void initInternal() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void startInternal() throws LifecycleException {
		Valve current = first;
        if (current == null) {
            current = basic;
        }
        while (current != null) {
            if (current instanceof Lifecycle)
                ((Lifecycle) current).start();
            current = current.getNext();
        }	
        
        setState(LifecycleState.STARTING);
	}

	@Override
	protected void stopInternal() throws LifecycleException {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void destroyInternal() throws LifecycleException {
		// TODO Auto-generated method stub
		
	}
}
