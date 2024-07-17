package gay.lemmaeof.terrifictickets.item;

import gay.lemmaeof.terrifictickets.TerrificTickets;
import gay.lemmaeof.terrifictickets.component.PasscardComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.StackReference;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.screen.slot.Slot;
import net.minecraft.text.Text;
import net.minecraft.util.ClickType;
import net.minecraft.util.Pair;

import java.util.List;

public class PasscardItem extends Item {
	public PasscardItem(Settings settings) {
		super(settings);
	}

	//TODO: onStackClicked like this too
	@Override
	public boolean onClicked(ItemStack stack, ItemStack otherStack, Slot slot, ClickType clickType, PlayerEntity player, StackReference cursorStackReference) {
		if (clickType == ClickType.RIGHT) {
			if (otherStack.isOf(TerrificTickets.TOKEN)) {
				stack.set(TerrificTickets.PASSCARD_COMPONENT, stack.get(TerrificTickets.PASSCARD_COMPONENT).addTokens(otherStack.getCount()));
				otherStack.setCount(0);
				return true;
			} else if (otherStack.isOf(TerrificTickets.TICKET)) {
				stack.set(TerrificTickets.PASSCARD_COMPONENT, stack.get(TerrificTickets.PASSCARD_COMPONENT).addTickets(otherStack.getCount()));
				otherStack.setCount(0);
				return true;
			} else if (otherStack.isEmpty()) {
				Pair<PasscardComponent, Integer> modified = stack.get(TerrificTickets.PASSCARD_COMPONENT).removeTickets(TerrificTickets.TICKET.getMaxCount());
				if (modified.getRight() > 0) {
					stack.set(TerrificTickets.PASSCARD_COMPONENT, modified.getLeft());
					cursorStackReference.set(new ItemStack(TerrificTickets.TICKET, modified.getRight()));
					return true;
				}
				return false;
			}
		}
		return super.onClicked(stack, otherStack, slot, clickType, player, cursorStackReference);
	}

	@Override
	public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
		super.appendTooltip(stack, context, tooltip, type);
		stack.get(TerrificTickets.PASSCARD_COMPONENT).appendTooltip(context, tooltip::add, type);
	}
}
