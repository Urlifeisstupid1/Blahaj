package hibi.blahaj;

import net.minecraft.component.*;
import net.minecraft.registry.*;
import net.minecraft.text.*;
import net.minecraft.util.*;

import java.util.function.*;

import eu.pb4.polymer.core.api.other.PolymerComponent;

public class BlahajDataComponentTypes {

	public static final ComponentType<Text> OWNER = register("owner",
		builder -> builder
			// pick a generous max length; exact value is your design choice
			.codec(TextCodecs.withJsonLengthLimit(262144))
			.packetCodec(TextCodecs.UNLIMITED_REGISTRY_PACKET_CODEC)
			.cache());

	private static <T> ComponentType<T> register(String id, UnaryOperator<ComponentType.Builder<T>> builderOperator) {
		return Registry.register(Registries.DATA_COMPONENT_TYPE, Identifier.of(Blahaj.MOD_ID, id), builderOperator.apply(ComponentType.builder()).build());
	}

	public static void register() {
		PolymerComponent.registerDataComponent(OWNER);
	}

}
