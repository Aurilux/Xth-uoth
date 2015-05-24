package aurilux.xthuoth.common.network;

import aurilux.xthuoth.common.core.Xthuoth;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;

/**
 * This class was created by <Aurilux>. It's distributed as part of the Xth'uoth Mod.
 * <p/>
 * Xth'uoth is Open Source and distributed under the GNU Lesser General Public License v3.0
 * (https://www.gnu.org/licenses/lgpl.html)
 * <p/>
 * File Created @ [12 Jan 2015]
 */
public class PacketDispatcher {
    /**
     * A simple counter will allow us to get rid of 'magic' numbers used during packet registration.
     * Using an incrementing field instead of hard-coded numerals, I don't need to think about what number comes
     * next or if I missed on should I ever rearrange the order of registration (if you wanted to alphabetize)
     */
    private static byte packetId = 0;
    private static final SimpleNetworkWrapper dispatcher = NetworkRegistry.INSTANCE.newSimpleChannel(Xthuoth.MOD_ID);

    /** Call this during pre-init or loading and register all of your packets (messages) here */
    public static final void init() {
        //register mod-unique packets
        //dispatcher.registerMessage(packet, packet, packetId++, side);
    }

    //// WRAPPERS
    /** Send this message to the specified player. */
    public static final void sendTo(IMessage message, EntityPlayerMP player) {
        dispatcher.sendTo(message, player);
    }

    /** Send this message to everyone within a certain range of a point. */
    public static final void sendToAllAround(IMessage message, NetworkRegistry.TargetPoint point) {
        dispatcher.sendToAllAround(message, point);
    }

    /** Sends a message to everyone within a certain range of the coordinates in the same dimension. */
    public static final void sendToAllAround(IMessage message, int dimension, double x, double y, double z, double range) {
        PacketDispatcher.sendToAllAround(message, new NetworkRegistry.TargetPoint(dimension, x, y, z,  range));
    }

    /** Sends a message to everyone within a certain range of the player provided. */
    public static final void sendToAllAround(IMessage message, EntityPlayer player, double range) {
        PacketDispatcher.sendToAllAround(message, player.worldObj.provider.dimensionId, player.posX, player.posY, player.posZ, range);
    }

    /** Send this message to everyone within the supplied dimension. */
    public static final void sendToDimension(IMessage message, int dimensionId) {
        dispatcher.sendToDimension(message, dimensionId);
    }

    /** Send this message to the server. */
    public static final void sendToServer(IMessage message) {
        dispatcher.sendToServer(message);
    }
    //// END WRAPPERS
}
