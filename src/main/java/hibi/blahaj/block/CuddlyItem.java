package hibi.blahaj.block;

import hibi.blahaj.Blahaj;
import hibi.blahaj.BlahajDataComponentTypes;
import net.minecraft.block.Block;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.component.type.TooltipDisplayComponent;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.minecraft.component.DataComponentTypes;

import eu.pb4.factorytools.api.item.FactoryBlockItem;
import eu.pb4.polymer.core.api.item.PolymerItem;
import eu.pb4.polymer.core.api.item.PolymerItemUtils;
import xyz.nucleoid.packettweaker.PacketContext;

import java.util.function.Consumer;

public class CuddlyItem extends FactoryBlockItem implements PolymerItem {

	private final Text tooltip;

	// Note: T is now actually used, no cast hacks
	public <T extends Block & eu.pb4.polymer.core.api.block.PolymerBlock> CuddlyItem(
		T block,
		Settings settings,
		String tooltipKey
	) {
		super(block, settings);
		this.tooltip = tooltipKey == null ? null
			: Text.translatable(tooltipKey).formatted(Formatting.GRAY);
		PolymerItemUtils.enableStonecutterFix();
	}

	@Override
	public void onCraft(ItemStack stack, World world) {
		super.onCraft(stack, world);
		// if you later want to set owner components, do it here
	}

	// Polymer: vanilla fallback item for clients
	@Override
	public Item getPolymerItem(ItemStack stack, PacketContext context) {
		// any vanilla helmet works; choose what you like
		return Items.LEATHER_HELMET;
	}

	@Deprecated
	@Override
	public void appendTooltip(ItemStack stack,
							  Item.TooltipContext context,
							  TooltipDisplayComponent displayComponent,
							  Consumer<Text> textConsumer,
							  TooltipType type) {
		// vanilla behaviour first
		super.appendTooltip(stack, context, displayComponent, textConsumer, type);

		if (this.tooltip != null) {
			textConsumer.accept(this.tooltip);
		}

		Text ownerName = stack.get(BlahajDataComponentTypes.OWNER);
		if (ownerName != null) {
			Text customName = stack.get(DataComponentTypes.CUSTOM_NAME);
			if (customName == null) {
				textConsumer.accept(
					Text.translatable("tooltip.blahaj.owner.craft", ownerName)
						.formatted(Formatting.GRAY)
				);
			} else {
				textConsumer.accept(
					Text.translatable("tooltip.blahaj.owner.rename", customName, ownerName)
						.formatted(Formatting.GRAY)
				);
			}
		}
	}

	public static final Identifier MINING_SPEED_MODIFIER_ID =
		Identifier.of(Blahaj.MOD_ID, "base_attack_damage");

	public static AttributeModifiersComponent createAttributeModifiers() {
		return AttributeModifiersComponent.builder()
			.add(EntityAttributes.BLOCK_BREAK_SPEED,
				new EntityAttributeModifier(
					MINING_SPEED_MODIFIER_ID,
					-3.0,
					EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL),
				AttributeModifierSlot.MAINHAND)
			.add(EntityAttributes.ATTACK_DAMAGE,
				new EntityAttributeModifier(
					BASE_ATTACK_DAMAGE_MODIFIER_ID,
					-2.0,
					EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL),
				AttributeModifierSlot.MAINHAND)
			.build();
	}
}
