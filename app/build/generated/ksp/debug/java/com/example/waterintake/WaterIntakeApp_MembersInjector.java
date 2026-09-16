package com.example.waterintake;

import androidx.hilt.work.HiltWorkerFactory;
import dagger.MembersInjector;
import dagger.internal.DaggerGenerated;
import dagger.internal.InjectedFieldSignature;
import dagger.internal.Provider;
import dagger.internal.Providers;
import dagger.internal.QualifierMetadata;
import javax.annotation.processing.Generated;

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
public final class WaterIntakeApp_MembersInjector implements MembersInjector<WaterIntakeApp> {
  private final Provider<HiltWorkerFactory> workerFactoryProvider;

  public WaterIntakeApp_MembersInjector(Provider<HiltWorkerFactory> workerFactoryProvider) {
    this.workerFactoryProvider = workerFactoryProvider;
  }

  public static MembersInjector<WaterIntakeApp> create(
      Provider<HiltWorkerFactory> workerFactoryProvider) {
    return new WaterIntakeApp_MembersInjector(workerFactoryProvider);
  }

  public static MembersInjector<WaterIntakeApp> create(
      javax.inject.Provider<HiltWorkerFactory> workerFactoryProvider) {
    return new WaterIntakeApp_MembersInjector(Providers.asDaggerProvider(workerFactoryProvider));
  }

  @Override
  public void injectMembers(WaterIntakeApp instance) {
    injectWorkerFactory(instance, workerFactoryProvider.get());
  }

  @InjectedFieldSignature("com.example.waterintake.WaterIntakeApp.workerFactory")
  public static void injectWorkerFactory(WaterIntakeApp instance, HiltWorkerFactory workerFactory) {
    instance.workerFactory = workerFactory;
  }
}
