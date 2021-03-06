/*
 * GNU LESSER GENERAL PUBLIC LICENSE
 *                       Version 3, 29 June 2007
 *
 * Copyright (C) 2007 Free Software Foundation, Inc. <http://fsf.org/>
 * Everyone is permitted to copy and distribute verbatim copies
 * of this license document, but changing it is not allowed.
 *
 * You can view LICENCE file for details. 
 *
 * @author The Dragonet Team
 */
package org.dragonet.proxy.network.translator.pe;

import com.github.steveice10.packetlib.packet.Packet;
import org.dragonet.proxy.network.InventoryTranslatorRegister;
import org.dragonet.proxy.network.UpstreamSession;
import org.dragonet.proxy.network.translator.PEPacketTranslator;
import sul.protocol.pocket113.play.ContainerClose;

public class PEWindowClosePacketTranslator implements PEPacketTranslator<ContainerClose> {

    @Override
    public Packet[] translate(UpstreamSession session, ContainerClose packet) {
        session.getProxy().getGeneralThreadPool().execute(() -> {
            InventoryTranslatorRegister.closeOpened(session, false);
        });
        return null;
    }

}
