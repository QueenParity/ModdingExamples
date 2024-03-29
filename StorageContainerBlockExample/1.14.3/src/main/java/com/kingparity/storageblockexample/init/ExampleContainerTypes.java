package com.kingparity.storageblockexample.init;

import com.kingparity.storageblockexample.Reference;
import com.kingparity.storageblockexample.gui.container.ExampleStorageContainer;
import com.kingparity.storageblockexample.gui.screen.ExampleStorageScreen;
import net.minecraft.client.gui.IHasContainer;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.network.IContainerFactory;
import net.minecraftforge.registries.ObjectHolder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Mod.EventBusSubscriber(modid = Reference.ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ExampleContainerTypes
{
    //An instance of our container as a ContainerType
    @ObjectHolder(Reference.ID + ":example_storage")
    public static final ContainerType<ExampleStorageContainer> EXAMPLE_STORAGE = null;
    
    //The list of the containers we want to register as ContainerType
    private static final List<ContainerType<?>> CONTAINER_TYPES = new ArrayList<>();
    
    public static <T extends Container> void add(ContainerType<T> type)
    {
        CONTAINER_TYPES.add(type);
    }
    
    public static List<ContainerType<?>> getContainerTypes()
    {
        return Collections.unmodifiableList(CONTAINER_TYPES);
    }
    
    @SubscribeEvent
    public static void addContainerTypes(final RegistryEvent.Register<ContainerType<?>> event)
    {
        register("example_storage", ExampleStorageContainer::new);
        CONTAINER_TYPES.forEach(container_type -> event.getRegistry().register(container_type));
    }
    
    public static void bindScreens(FMLClientSetupEvent event)
    {
        bindScreen(EXAMPLE_STORAGE, ExampleStorageScreen::new);
    }
    
    private static <T extends Container> void register(String name, IContainerFactory<T> container)
    {
        ContainerType<T> type = IForgeContainerType.create(container);
        type.setRegistryName(name);
        CONTAINER_TYPES.add(type);
    }
    
    private static <M extends Container, U extends Screen & IHasContainer<M>> void bindScreen(ContainerType<M> container, ScreenManager.IScreenFactory<M, U> screen)
    {
        ScreenManager.registerFactory(container, screen);
    }
}