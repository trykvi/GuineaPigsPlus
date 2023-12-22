package net.tbkiron.guineapigsplus.entity.custom;

import net.minecraft.Util;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.scores.Team;
import net.minecraftforge.event.ForgeEventFactory;
import net.tbkiron.guineapigsplus.entity.ModEntities;
import net.tbkiron.guineapigsplus.entity.client.GuineaPigModel;
import net.tbkiron.guineapigsplus.entity.variant.GuineaPigVariant;
import org.jetbrains.annotations.Nullable;
import org.stringtemplate.v4.ST;

public class GuineaPigEntity extends TamableAnimal {


    public final AnimationState sitToStandAnimationState = new AnimationState();
    public final AnimationState standToSitAnimationState = new AnimationState();
    public final AnimationState fourToTwoAnimationState = new AnimationState();
    public final AnimationState twoToFourAnimationState = new AnimationState();

    private static final int SIT_DURATION_TICKS = 3;
    private static final int STAND_DURATION_TICKS = 7;
    private int sit_state_cooldown = 0;
    private int stand_state_cooldown = 0;

    private static final EntityDataAccessor<Boolean> SITTING =
            SynchedEntityData.defineId(GuineaPigEntity.class, EntityDataSerializers.BOOLEAN);

    private static final EntityDataAccessor<Boolean> STANDING_ON_TWO =
            SynchedEntityData.defineId(GuineaPigEntity.class, EntityDataSerializers.BOOLEAN);

    private static final EntityDataAccessor<Integer> DATA_ID_TYPE_VARIANT =
            SynchedEntityData.defineId(GuineaPigEntity.class, EntityDataSerializers.INT);


    private int idleAnimationTimeout = 0;

    @Override
    public void tick() {
        super.tick();

        if(this.level().isClientSide()){
            setupAnimationStates();
        }
    }

    private void setupAnimationStates(){
        if(stand_state_cooldown > 0){
            if(isStandingOnTwo()){
                this.fourToTwoAnimationState.startIfStopped(this.tickCount);
            } else {
                this.twoToFourAnimationState.startIfStopped(this.tickCount);
            }
            stand_state_cooldown--;
        } else {
            this.fourToTwoAnimationState.stop();
            this.twoToFourAnimationState.stop();
        }

        if(sit_state_cooldown > 0){
            if(isSitting()){
                this.standToSitAnimationState.startIfStopped(this.tickCount);
            } else {
                this.sitToStandAnimationState.startIfStopped(this.tickCount);
            }
            sit_state_cooldown--;
        } else {
            this.sitToStandAnimationState.stop();
            this.standToSitAnimationState.stop();
        }

    }

    @Override
    protected void updateWalkAnimation(float pPartialTick) {
        float f;
        if(this.getPose() != Pose.SITTING){
            f = Math.min(pPartialTick * 6F, 1F);
        } else {
            f = 0f;
        }

        this.walkAnimation.update(f, 0.2f);
    }

    public GuineaPigEntity(EntityType<? extends TamableAnimal> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Override
    protected void registerGoals() {
        if(this.getVariant() == GuineaPigVariant.FREDAG){
            this.goalSelector.addGoal(1, new FloatGoal(this));
            this.goalSelector.addGoal(2, new SitWhenOrderedToGoal(this));
            this.goalSelector.addGoal(3, new PanicGoal(this, 1.25D));
            this.goalSelector.addGoal(4, new BreedGoal(this, 1.15D));
            this.goalSelector.addGoal(5, new TemptGoal(this, 1.2D, Ingredient.of(Items.GRASS), false));
            this.goalSelector.addGoal(5, new TemptGoal(this, 1.2D, Ingredient.of(Items.GOLDEN_CARROT), false));
            this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0F));
            this.goalSelector.addGoal(7, new FollowOwnerGoal(this, 1.0D, 10.0F, 2.0F, false));
            this.goalSelector.addGoal(8, new FollowParentGoal(this, 1.1D));
            this.goalSelector.addGoal(9, new WaterAvoidingRandomStrollGoal(this, 1.0D));
            this.goalSelector.addGoal(10, new RandomLookAroundGoal(this));
            this.goalSelector.addGoal(11, (new HurtByTargetGoal(this)).setAlertOthers());
        } else {
            this.goalSelector.addGoal(1, new FloatGoal(this));
            this.goalSelector.addGoal(2, new SitWhenOrderedToGoal(this));
            this.goalSelector.addGoal(3, new PanicGoal(this, 1.25D));
            this.goalSelector.addGoal(4, new BreedGoal(this, 1.15D));
            this.goalSelector.addGoal(5, new TemptGoal(this, 1.2D, Ingredient.of(Items.GRASS), false));
            this.goalSelector.addGoal(5, new TemptGoal(this, 1.2D, Ingredient.of(Items.GOLDEN_CARROT), false));
            this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0F));
            this.goalSelector.addGoal(7, new FollowOwnerGoal(this, 1.0D, 10.0F, 2.0F, false));
            this.goalSelector.addGoal(8, new FollowParentGoal(this, 1.1D));
            this.goalSelector.addGoal(9, new WaterAvoidingRandomStrollGoal(this, 1.0D));
            this.goalSelector.addGoal(10, new RandomLookAroundGoal(this));
            this.goalSelector.addGoal(11, (new HurtByTargetGoal(this)).setAlertOthers());
        }

    }

    public static AttributeSupplier.Builder createAttributes(){
        return TamableAnimal.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 10)
                .add(Attributes.FOLLOW_RANGE, 24D)
                .add(Attributes.MOVEMENT_SPEED, 0.15D);

    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel pLevel, AgeableMob pOtherParent) {
        return ModEntities.GUINEA_PIG.get().create(pLevel);
    }

    @Override
    public boolean isFood(ItemStack pStack) {
        return pStack.is(Items.GRASS);
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.FOX_AMBIENT;
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource pDamageSource) {
        return SoundEvents.FOX_HURT;
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.FOX_DEATH;
    }

    @Override
    protected float getSoundVolume() {
        return 0.2F;
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);
        Item item = itemStack.getItem();

        Item itemForTaming = Items.GOLDEN_CARROT;



        if(item == itemForTaming && !isTame()){
            if(this.level().isClientSide){
                return InteractionResult.CONSUME;
            } else {
                if(!player.getAbilities().instabuild){
                    itemStack.shrink(1);
                }

                if(!ForgeEventFactory.onAnimalTame(this, player)){
                    if(!this.level().isClientSide) {
                        super.tame(player);
                        this.navigation.recomputePath();
                        this.setTarget(null);
                        this.level().broadcastEntityEvent(this, (byte) 7);
                        setSitting(true);
                    }
                }

                return InteractionResult.SUCCESS;
            }
        }

        if(item == Items.STICK && !this.isSitting()) {
            this.setStandOnTwo(!this.isStandingOnTwo());
        } else if(isTame() && !this.level().isClientSide && hand == InteractionHand.MAIN_HAND && this.isOwnedBy(player)){
            if(!this.isStandingOnTwo()){
                setSitting(!isSitting());
            }


            return InteractionResult.SUCCESS;
        }

        if(itemStack.getItem() == itemForTaming){
            return InteractionResult.PASS;
        }

        return super.mobInteract(player, hand);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        setSitting(pCompound.getBoolean("isSitting"));
        setStandOnTwo(pCompound.getBoolean("isStandingOnTwo"));
        this.entityData.set(DATA_ID_TYPE_VARIANT, pCompound.getInt("Variant"));
    }

    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putBoolean("isSitting", this.isSitting());
        pCompound.putBoolean("isStandingOnTwo", this.isStandingOnTwo());
        pCompound.putInt("Variant", this.getTypeVariant());
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(SITTING, false);
        this.entityData.define(STANDING_ON_TWO, false);
        this.entityData.define(DATA_ID_TYPE_VARIANT, 0);
    }

    public void setStandOnTwo(boolean standOnTwo){
        this.entityData.set(STANDING_ON_TWO, standOnTwo);
    }

    public boolean isStandingOnTwo(){
        return this.entityData.get(STANDING_ON_TWO);
    }

    public void setSitting(boolean sitting){
        this.entityData.set(SITTING, sitting);
        this.setOrderedToSit(sitting);
    }

    public boolean isSitting(){
        return this.entityData.get(SITTING);
    }

    @Override
    public Team getTeam() {
        return super.getTeam();
    }

    @Override
    public boolean canBeLeashed(Player pPlayer) {
        return true;
    }

    public SpawnGroupData finalizeSpawn(ServerLevelAccessor slAccessor, DifficultyInstance difficultyInstance,
                                        MobSpawnType spawnType, @Nullable SpawnGroupData spawnGroupData,
                                        @Nullable CompoundTag tag){
        GuineaPigVariant variant = Util.getRandom(GuineaPigVariant.values(), this.random);
        setVariant(variant);
        return super.finalizeSpawn(slAccessor, difficultyInstance, spawnType, spawnGroupData, tag);
    }

    public GuineaPigVariant getVariant(){
        return GuineaPigVariant.byId(this.getTypeVariant() & 255);
    }

    private int getTypeVariant(){
        return this.entityData.get(DATA_ID_TYPE_VARIANT);
    }

    private void setVariant(GuineaPigVariant variant){
        this.entityData.set(DATA_ID_TYPE_VARIANT, variant.getId() & 255);
    }


}
