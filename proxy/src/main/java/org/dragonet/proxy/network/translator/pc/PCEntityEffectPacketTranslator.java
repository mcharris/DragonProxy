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
package org.dragonet.proxy.network.translator.pc;

import com.github.steveice10.mc.protocol.data.MagicValues;
import com.github.steveice10.mc.protocol.packet.ingame.server.entity.ServerEntityEffectPacket;
import org.dragonet.PocketPotionEffect;
import org.dragonet.proxy.network.CacheKey;
import org.dragonet.proxy.network.UpstreamSession;
import org.dragonet.proxy.network.cache.CachedEntity;
import org.dragonet.proxy.network.translator.PCPacketTranslator;
import sul.protocol.pocket113.play.MobEffect;
import sul.utils.Packet;

public class PCEntityEffectPacketTranslator implements PCPacketTranslator<ServerEntityEffectPacket> {

    @Override
    public Packet[] translate(UpstreamSession session, ServerEntityEffectPacket packet) {
        CachedEntity entity = session.getEntityCache().get(packet.getEntityId());
        if (entity == null) {
            return null;
        }
        int effectId = MagicValues.value(Integer.class, packet.getEffect());

        MobEffect eff = new MobEffect();
        eff.entityId = packet.getEntityId() == (int) session.getDataCache().get(CacheKey.PLAYER_EID) ? 0 : packet.getEntityId();
        eff.effect = PocketPotionEffect.getByID(effectId).getEffect();
        if (entity.effects.contains(effectId)) {
            eff.eventId = MobEffect.MODIFY;
        } else {
            eff.eventId = MobEffect.ADD;
            entity.effects.add(effectId);
        }
        eff.amplifier = packet.getAmplifier();
        eff.duration = packet.getDuration();
        eff.particles = packet.getShowParticles();
        return new Packet[]{eff};
    }

}
