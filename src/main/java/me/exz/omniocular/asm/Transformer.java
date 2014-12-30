package me.exz.omniocular.asm;

import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

public class Transformer implements IClassTransformer {
    @Override
    public byte[] transform(String name, String transformedName, byte[] bytes) {
        if (name.equals("mcp.mobius.waila.handlers.HUDHandlerFMP")) {
            ClassReader cr = new ClassReader(bytes);
            ClassNode cn = new ClassNode();
            cr.accept(cn, 0);
            for (MethodNode mn : cn.methods) {
                if (mn.name.equals("getWailaBody")) {
                    AbstractInsnNode n = mn.instructions.getFirst();
                    while (n != null) {
                        if (n.getOpcode() == Opcodes.ASTORE && ((VarInsnNode) n).var == 8) {
                            n = n.getNext();
                            break;
                        }
                        n = n.getNext();
                    }
                    //TODO better fmp support
                    if (n != null) {
                        mn.instructions.insertBefore(n, new FieldInsnNode(Opcodes.GETSTATIC, "mcp/mobius/waila/api/impl/DataAccessorFMP", "instance", "Lmcp/mobius/waila/api/impl/DataAccessorFMP;"));
                        mn.instructions.insertBefore(n, new VarInsnNode(Opcodes.ALOAD, 3));
                        mn.instructions.insertBefore(n, new MethodInsnNode(Opcodes.INVOKEINTERFACE, "mcp/mobius/waila/api/IWailaDataAccessor", "getWorld", "()Lnet/minecraft/world/World;", true));
                        mn.instructions.insertBefore(n, new VarInsnNode(Opcodes.ALOAD, 3));
                        mn.instructions.insertBefore(n, new MethodInsnNode(Opcodes.INVOKEINTERFACE, "mcp/mobius/waila/api/IWailaDataAccessor", "getPlayer", "()Lnet/minecraft/entity/player/EntityPlayer;", true));
                        mn.instructions.insertBefore(n, new VarInsnNode(Opcodes.ALOAD, 3));
                        mn.instructions.insertBefore(n, new MethodInsnNode(Opcodes.INVOKEINTERFACE, "mcp/mobius/waila/api/IWailaDataAccessor", "getPosition", "()Lnet/minecraft/util/MovingObjectPosition;", true));
                        mn.instructions.insertBefore(n, new VarInsnNode(Opcodes.ALOAD, 7));
                        mn.instructions.insertBefore(n, new VarInsnNode(Opcodes.ALOAD, 8));
                        mn.instructions.insertBefore(n, new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "mcp/mobius/waila/api/impl/DataAccessorFMP", "set", "(Lnet/minecraft/world/World;Lnet/minecraft/entity/player/EntityPlayer;Lnet/minecraft/util/MovingObjectPosition;Lnet/minecraft/nbt/NBTTagCompound;Ljava/lang/String;)V", false));
                        mn.instructions.insertBefore(n, new VarInsnNode(Opcodes.ALOAD, 2));
                        mn.instructions.insertBefore(n, new FieldInsnNode(Opcodes.GETSTATIC, "mcp/mobius/waila/api/impl/DataAccessorFMP", "instance", "Lmcp/mobius/waila/api/impl/DataAccessorFMP;"));
                        mn.instructions.insertBefore(n, new MethodInsnNode(Opcodes.INVOKESTATIC, "me/exz/omniocular/handler/FMPHandler", "getWailaBody", "(Ljava/util/List;Lmcp/mobius/waila/api/IWailaFMPAccessor;)Ljava/util/List;", false));
                        mn.instructions.insertBefore(n, new VarInsnNode(Opcodes.ASTORE, 2));
                    }
//                    mn.instructions.clear();
//                    mn.instructions.add(new InsnNode(Opcodes.ICONST_1));
//                    mn.instructions.add(new InsnNode(Opcodes.IRETURN));
//                    mn.maxStack=1;
                }
            }
            ClassWriter cw = new ClassWriter(0);
            cn.accept(cw);
            //LogHelper.info("inject into waila");
            return cw.toByteArray();
        } else {
            return bytes;
        }
    }
}
