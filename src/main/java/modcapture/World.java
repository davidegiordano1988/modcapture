/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modcapture;

/**
 *
 * @author 107310
 */
public class World extends Thread {
 
    private String world;
 
    public World(String world) {
        this.world = world;
    }
 
    public String get() {
        return this.world;
    }
 
    @Override
    public void run() {
        this.world = this.world.toLowerCase();
    }
 
}
