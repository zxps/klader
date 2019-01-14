package org.rubio.klader.core.command.types;

public interface AtomicBus extends Bus {

    public void rollbackBusTransaction();

    public void beginBusTransaction();

    public void commitBusTransaction();
}
