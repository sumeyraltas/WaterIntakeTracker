package com.example.waterintake.notification;

import android.content.Context;
import androidx.work.WorkerParameters;
import com.example.waterintake.domain.repository.WaterRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Provider;
import dagger.internal.Providers;
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
public final class WaterReminderWorker_Factory {
  private final Provider<WaterRepository> repositoryProvider;

  public WaterReminderWorker_Factory(Provider<WaterRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  public WaterReminderWorker get(Context context, WorkerParameters workerParams) {
    return newInstance(context, workerParams, repositoryProvider.get());
  }

  public static WaterReminderWorker_Factory create(
      javax.inject.Provider<WaterRepository> repositoryProvider) {
    return new WaterReminderWorker_Factory(Providers.asDaggerProvider(repositoryProvider));
  }

  public static WaterReminderWorker_Factory create(Provider<WaterRepository> repositoryProvider) {
    return new WaterReminderWorker_Factory(repositoryProvider);
  }

  public static WaterReminderWorker newInstance(Context context, WorkerParameters workerParams,
      WaterRepository repository) {
    return new WaterReminderWorker(context, workerParams, repository);
  }
}
