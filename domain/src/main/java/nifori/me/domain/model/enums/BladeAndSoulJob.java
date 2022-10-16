package nifori.me.domain.model.enums;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum BladeAndSoulJob {

  BM("BM", "BladeMaster", 1),
  KFM("KFM", "KungFuMaster", 2),
  FM("FM", "ForceMaster", 3),
  GUN("GUN", "Gunner", 4),
  DES("DES", "Destroyer", 5),
  SUM("SUM", "Summoner", 6),
  SIN("SIN", "Assassin", 7),
  BD("BD", "BladeDancer", 8),
  WL("WL", "Warlock", 9),
  SF("SF", "SoulFist", 10),
  WD("WD", "Warden", 11),
  ARC("ARC", "Archer", 12);

  private String nameShort;
  private String name;
  private long id;

  private BladeAndSoulJob(String nameShort, String name, long id) {
    this.nameShort = nameShort;
    this.name = name;
    this.id = id;
  }

  public static BladeAndSoulJob getById(long id) {
    return Arrays.stream(values())
        .filter(job -> job.id == id)
        .findFirst()
        .orElseThrow();
  }

}
