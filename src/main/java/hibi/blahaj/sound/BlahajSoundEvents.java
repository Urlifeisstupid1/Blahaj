package hibi.blahaj.sound;

import hibi.blahaj.Blahaj;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.random.Random;

import java.util.ArrayList;
import java.util.List;

public class BlahajSoundEvents {

	public static final List<SoundEvent> BLOCK_CUDDLY_ITEM = new ArrayList<>();

	public static final SoundEvent BLOCK_CUDDLY_ITEM_HIT =
		register("block.blahaj.cuddly_item.hit");

	private static Identifier id(String path) {
		return Identifier.of(Blahaj.MOD_ID, path);
	}

	private static SoundEvent register(String path) {
		Identifier identifier = id(path);
		SoundEvent event = SoundEvent.of(identifier);
		return Registry.register(Registries.SOUND_EVENT, identifier, event);
	}

	public static void init() {
		for (int i = 1; i < 6; i++) {
			BLOCK_CUDDLY_ITEM.add(register("block.blahaj.cuddly_item.use." + i));
		}
	}

	public static SoundEvent getRandomSqueak(Random random) {
		return BLOCK_CUDDLY_ITEM.get(random.nextInt(BLOCK_CUDDLY_ITEM.size()));
	}
}
