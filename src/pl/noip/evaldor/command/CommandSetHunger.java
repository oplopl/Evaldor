package pl.noip.evaldor.command;


import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import pl.noip.evaldor.Evaldor;
import pl.noip.evaldor.Messages;

public class CommandSetHunger implements CommandExecutor {
	public boolean onCommand(CommandSender sender, Command command, String alias, String[] args) {
		if (args.length == 1) {
			if (!(sender instanceof Player)) {
				sender.sendMessage(Messages.playerOnly);
				return true;
			} else if (!Evaldor.hasPerm(sender, "evaldor.sethunger") && !Evaldor.hasPerm(sender, "evaldor.sethunger.others")) {
				Evaldor.noPerm(sender);
				return true;
			}
			try {
				int hp = Integer.parseInt(args[0]);
				if (hp < 0 || hp > 20) {
					sender.sendMessage(Messages.sethungerNotInteger);
					return true;
				}
				((Player) sender).setFoodLevel(hp);
				sender.sendMessage(Messages.sethungerSuccess.replaceAll("\\{player\\}", Evaldor.getName(sender)).replaceAll("\\{hunger\\}", args[0]));
			} catch (NumberFormatException e) {
				sender.sendMessage(Messages.sethungerNotInteger);
			}
			return true;
		} else if (args.length == 2) {
			if (!Evaldor.hasPerm(sender, "evaldor.sethunger.others")) {
				Evaldor.noPerm(sender);
				return true;
			}
			Player target = sender.getServer().getPlayer(args[0]);
			int hp;
			try {hp = Integer.parseInt(args[1]);} catch (NumberFormatException e) {
				sender.sendMessage(Messages.sethungerNotInteger);
				return true;
			}
			if (target != null) {
				try {
					if (hp < 0 || hp > 20) {
						sender.sendMessage(Messages.sethealthNotInteger);
						return true;
					}
					target.setFoodLevel(Integer.parseInt(args[1]));
					sender.sendMessage(Messages.sethungerSuccess.replaceAll("\\{player\\}", Evaldor.getName(target)).replaceAll("\\{hunger\\}", args[1]));
				} catch (NumberFormatException e) {
					sender.sendMessage(Messages.sethungerNotInteger);
				}
			} else {
				target = sender.getServer().getPlayer(args[1]);
				try {
					hp = Integer.parseInt(args[0]);
					if (hp < 0 || hp > 20) {
						sender.sendMessage(Messages.sethealthNotInteger);
						return true;
					}
					target.setFoodLevel(hp);
					sender.sendMessage(Messages.sethungerSuccess.replaceAll("\\{player\\}", Evaldor.getName(target)).replaceAll("\\{hunger\\}", args[0]));
				} catch (NumberFormatException e) {
					sender.sendMessage(Messages.sethungerNotInteger);
				}
			}
			return true;
		} else {
			sender.sendMessage(Messages.wrongUsage+command.getUsage());
			return true;
		}
	}

}
