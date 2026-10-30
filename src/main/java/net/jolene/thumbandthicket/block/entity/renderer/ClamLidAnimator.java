package net.jolene.thumbandthicket.block.entity.renderer;

import net.minecraft.util.math.MathHelper;

public class ClamLidAnimator {
	private boolean open;
	private float progress;
	private float lastProgress;

	public void step() {
		this.lastProgress = this.progress;
        if (!this.open && this.progress > 0.0F) {
			this.progress = Math.max(this.progress - 0.1F, 0.0F);
		} else if (this.open && this.progress < 1.0F) {
			this.progress = Math.min(this.progress + 0.1F, 1.0F);
		}
	}

	public float getProgress(float delta) {
		return MathHelper.lerp(delta, this.lastProgress, this.progress);
	}

	public void setOpen(boolean open) {
		this.open = open;
	}

	public void setOpenImmediately(boolean open) {
		this.open = open;
		this.progress = open ? 1.0F : 0.0F;
		this.lastProgress = this.progress;
	}
}