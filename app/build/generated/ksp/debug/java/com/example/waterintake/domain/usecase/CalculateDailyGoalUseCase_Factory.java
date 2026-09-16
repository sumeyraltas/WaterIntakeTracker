package com.example.waterintake.domain.usecase;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

@ScopeMetadata
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast",
    "deprecation",
    "nullness:initialization.field.uninitialized"
})
public final class CalculateDailyGoalUseCase_Factory implements Factory<CalculateDailyGoalUseCase> {
  @Override
  public CalculateDailyGoalUseCase get() {
    return newInstance();
  }

  public static CalculateDailyGoalUseCase_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static CalculateDailyGoalUseCase newInstance() {
    return new CalculateDailyGoalUseCase();
  }

  private static final class InstanceHolder {
    static final CalculateDailyGoalUseCase_Factory INSTANCE = new CalculateDailyGoalUseCase_Factory();
  }
}
