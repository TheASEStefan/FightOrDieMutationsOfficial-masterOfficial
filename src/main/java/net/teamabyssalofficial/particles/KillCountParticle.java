package net.teamabyssalofficial.particles;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class KillCountParticle extends TextureSheetParticle {

    KillCountParticle(ClientLevel pLevel, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed) {
        super(pLevel, pX, pY, pZ, pXSpeed, pYSpeed, pZSpeed);
        this.rCol = 1f;
        this.gCol = 1f;
        this.bCol = 1f;
        this.setSize(0.02F, 0.02F);
        this.quadSize *= this.random.nextFloat() * 0.6F + 1.2F;
        this.xd *= (double) 0.02F;
        this.yd *= (double) 0.02F;
        this.zd *= (double) 0.02F;
        this.lifetime = (int)(20.0D / (Math.random() * 0.8D + 0.2D));
        this.gravity = -25f * Mth.PI * Mth.EPSILON;
    }

    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    public void move(double pX, double pY, double pZ) {
        this.setBoundingBox(this.getBoundingBox().move(pX, pY, pZ));
        this.setLocationFromBoundingbox();
    }

    public void tick() {
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;
        if (this.lifetime-- <= 0) {
            this.remove();
        } else {
            this.move(this.xd, this.yd, this.zd);
            this.xd *= 0.99D;
            this.yd *= 0.99D;
            this.zd *= 0.99D;
        }
    }

    @OnlyIn(Dist.CLIENT)
    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprite;

        public Provider(SpriteSet pSprites) {
            this.sprite = pSprites;
        }

        public Particle createParticle(SimpleParticleType pType, ClientLevel pLevel, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed) {
            KillCountParticle killCountParticle = new KillCountParticle(pLevel, pX, pY, pZ, pXSpeed, pYSpeed, pZSpeed);
            killCountParticle.pickSprite(this.sprite);
            return killCountParticle;
        }
    }
}
