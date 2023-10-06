/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2.components;

import com.simsilica.es.EntityComponent;

/**
 *
 * @author codex
 */
public class Supplier implements EntityComponent {
    
    public static final String SET = "set", ADD = "add";
    
    private final int channel;
    private final int amount;
    private final String method;

    public Supplier(int channel, int amount) {
        this(channel, amount, ADD);
    }
    public Supplier(int channel, int amount, String method) {
        this.channel = channel;
        this.amount = amount;
        this.method = method;
    }

    public int getChannel() {
        return channel;
    }
    public int getAmount() {
        return amount;
    }
    public String getMethod() {
        return method;
    }
    public boolean isReplenish() {
        return amount < 0;
    }
    
}
