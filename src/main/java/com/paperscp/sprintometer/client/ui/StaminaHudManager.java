package com.paperscp.sprintometer.client.ui;

import com.paperscp.sprintometer.client.ActionStamina;
import com.paperscp.sprintometer.mixins.MinecraftClientMixin;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import static com.paperscp.sprintometer.SprintOMeter.client;

@Environment(EnvType.CLIENT)
public class StaminaHudManager {
    private int hudTimer = 0; // In Ticks
    private int posY = 0; // Stamina Icon Y Coordinate
    private int actionTimer = 0; // 3 Tick Delay For Checking "is_sprinting" & "is_jumping"

    public int staminaNumberCoords() { return posY == 20 ? 28 : 0; }

    public void staminaHudDelay() {
        int Stamina = ActionStamina.Stamina;

        if (actionTimer(1) || actionTimer(2) || Stamina != 100) {
            hudTimer = 23;
        } else if (hudTimer > 0) {
            hudTimer = hudTimer - 1;
        }
    }

    public int staminaNumberColor() {
        int Stamina = ActionStamina.Stamina;
        return Stamina <= 25 ? 0xFFFF00 : 0xFFFFFF;
    }

    public int staminaIconCoords() {
        int x = ((MinecraftClientMixin.MinecraftClientInterfaceMixin)client).getCurrentFPS();
        double y = 140.0 / x;
        int z = (int) Math.round(y);

        int Stamina = ActionStamina.Stamina;

        if (Stamina != 100) {
            if (posY == 20) {return posY;}
            if (!(posY >= 20)) { posY = posY + z; }
            if (posY > 20) {posY = 20;}

            return posY;

        } else if (hudTimer > 0) {

            return posY;

        } else {
            if (posY == 0) {return posY;}
            if (!(posY <= 0)) { posY = posY - z; }
            if (posY < 0) {posY = 0;}

            return posY;
        }
    }

    private boolean actionTimer(int ix) {

        boolean is_sprinting = client.player.isSprinting();
        boolean is_jumping = ActionStamina.isJumpKeyPressed;

        if (actionTimer == 3) { actionTimer = 0; return ix == 1 ? is_sprinting : is_jumping; }
        else {actionTimer++; return false;}
    }

    public int staminaValue() {
        return ActionStamina.Stamina < 0 ? 0 : Math.min(ActionStamina.Stamina, 100);

    }
}
