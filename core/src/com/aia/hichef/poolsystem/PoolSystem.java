package com.aia.hichef.poolsystem;

import com.badlogic.gdx.utils.Pool;

public class PoolSystem {
	private static PoolSystem	instance;
	public Pool<ItemFood>		itemFoodPool;

	private PoolSystem() {
		super();
		itemFoodPool = new Pool<ItemFood>(10, 50) {
			@Override
			protected ItemFood newObject() {
				final ItemFood prototype = new ItemFood();
				prototype.setPoolListener(new PoolListener() {

					@Override
					public void onDispose() {
						itemFoodPool.free(prototype);
					}
				});
				return prototype;
			}
		};
	}

	public static PoolSystem getInstance() {
		if (instance == null)
			instance = new PoolSystem();
		return instance;
	}
}
