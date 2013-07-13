package com.Acrobot.ChestShop.Listeners.Economy;

import com.Acrobot.ChestShop.ChestShop;
import com.Acrobot.ChestShop.Configuration.Properties;
import com.Acrobot.ChestShop.Events.Economy.*;
import com.Acrobot.ChestShop.Signs.ChestShopSign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import java.math.BigDecimal;

/**
 * @author Acrobot
 */
public class ServerAccountCorrector implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public static void onCurrencyAdd(CurrencyAddEvent event) {
        String target = event.getTarget();

        if (!ChestShopSign.isAdminShop(target)) {
            return;
        }

        if (Properties.SERVER_ECONOMY_ACCOUNT.isEmpty()) {
            event.setAdded(true);
            return;
        } else {
            target = Properties.SERVER_ECONOMY_ACCOUNT;
        }

        event.setAdded(true);

        CurrencyAddEvent currencyAddEvent = new CurrencyAddEvent(event.getAmount(), target, event.getWorld());
        ChestShop.callEvent(currencyAddEvent);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public static void onCurrencySubtract(CurrencySubtractEvent event) {
        String target = event.getTarget();

        if (!ChestShopSign.isAdminShop(target)) {
            return;
        }

        if (Properties.SERVER_ECONOMY_ACCOUNT.isEmpty()) {
            event.setSubtracted(true);
            return;
        } else {
            target = Properties.SERVER_ECONOMY_ACCOUNT;
        }

        event.setSubtracted(true);

        CurrencySubtractEvent currencySubtractEvent = new CurrencySubtractEvent(event.getAmount(), target, event.getWorld());
        ChestShop.callEvent(currencySubtractEvent);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public static void onCurrencyCheck(CurrencyCheckEvent event) {
        String target = event.getAccount();

        if (!ChestShopSign.isAdminShop(target)) {
            return;
        }

        if (Properties.SERVER_ECONOMY_ACCOUNT.isEmpty()) {
            event.hasEnough(true);
            return;
        } else {
            target = Properties.SERVER_ECONOMY_ACCOUNT;
        }

        CurrencyCheckEvent currencyCheckEvent = new CurrencyCheckEvent(event.getAmount(), target, event.getWorld());
        ChestShop.callEvent(currencyCheckEvent);

        event.hasEnough(currencyCheckEvent.hasEnough());
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public static void onBalanceCheck(CurrencyAmountEvent event) {
        String target = event.getAccount();

        if (!ChestShopSign.isAdminShop(target)) {
            return;
        }

        if (Properties.SERVER_ECONOMY_ACCOUNT.isEmpty()) {
            event.setAmount(BigDecimal.valueOf(Double.MAX_VALUE));
            return;
        } else {
            target = Properties.SERVER_ECONOMY_ACCOUNT;
        }

        CurrencyAmountEvent currencyAmountEvent = new CurrencyAmountEvent(target, event.getWorld());
        ChestShop.callEvent(currencyAmountEvent);

        event.setAmount(currencyAmountEvent.getAmount());
    }
}
