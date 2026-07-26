package net.why.tutorialmod;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.why.tutorialmod.block.ModBlocks;
import net.why.tutorialmod.item.ModCreativeModeTabs;
import net.why.tutorialmod.item.ModItems;
import org.slf4j.Logger;
import com.mojang.logging.LogUtils;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;


// 此处填写的modid必须和 META-INF/neoforge.mods.toml 文件内配置保持一致
@Mod(TutorialMod.MOD_ID)
public class TutorialMod {
    // 统一定义模组ID，方便项目各处引用
    public static final String MOD_ID = "tutorialmod";
    // slf4j日志对象，用于控制台输出日志
    public static final Logger LOGGER = LogUtils.getLogger();

    // 模组主类构造方法，是模组加载时最先执行的代码
    // FML能够自动识别部分参数类型（例如 IEventBus、ModContainer）并自动传入实例
    public TutorialMod(IEventBus modEventBus, ModContainer modContainer) {
        // 注册commonSetup方法，模组加载生命周期内触发
        modEventBus.addListener(this::commonSetup);

        // 将当前类注册到NeoForge全局事件总线，用于接收游戏各类事件
        // 注意：仅当本类存在带@SubscribeEvent的事件方法时才需要这一行
        // 如果本类没有类似onServerStarting()这类事件方法，可以删除本行
        NeoForge.EVENT_BUS.register(this);

        ModCreativeModeTabs.register(modEventBus);
        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);

        // 监听创造标签页构建事件，向创造栏添加物品
        modEventBus.addListener(this::addCreative);

        // 注册模组配置文件规范，FML会自动创建并加载配置文件
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(FMLCommonSetupEvent event) {

    }

    // 将方块物品添加至创造模式【建筑方块】标签页
    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if(event.getTabKey() == CreativeModeTabs.INGREDIENTS) {
            event.accept(ModItems.BISMUTH);
            event.accept(ModItems.RAW_BISMUTH);
        }
        if(event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS) {
            event.accept(ModBlocks.BISMUTH_BLOCK);
            event.accept(ModBlocks.BISMUTH_ORE);
        }
    }

    // 使用 @SubscribeEvent 注解，事件总线会自动调用该方法
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {

    }

}
