package me.exz.omniocular.asm;

import cpw.mods.fml.relauncher.IFMLLoadingPlugin;

import java.util.Map;

@IFMLLoadingPlugin.MCVersion("1.7.10")
public class Core implements IFMLLoadingPlugin {
    @Override
    public String[] getASMTransformerClass() {
        //LogHelper.info("getASMTransformerClass");
        return new String[]{Transformer.class.getName()};
    }

    @Override
    public String getModContainerClass() {
        return CoreContainer.class.getName();
    }

    @Override
    public String getSetupClass() {
        return null;
    }

    @Override
    public void injectData(Map<String, Object> data) {

    }

    @Override
    public String getAccessTransformerClass() {
        return null;
    }
}
