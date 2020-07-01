package com.mina_mikhail.fixed_solutions_task.data.model;

import com.mina_mikhail.fixed_solutions_task.utils.Constants;

public class ProductionCompany {

  /**
   * id : 82819
   * logo_path : /5Z8WWr0Lf1tInVWwJsxPP0uMz9a.png
   * name : Skydance Media
   * origin_country : US
   */

  private int id;
  private String logo_path;
  private String name;
  private String origin_country;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getLogo_path() {
    return Constants.IMAGES_BASE_URL + logo_path;
  }

  public void setLogo_path(String logo_path) {
    this.logo_path = logo_path;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getOrigin_country() {
    return origin_country;
  }

  public void setOrigin_country(String origin_country) {
    this.origin_country = origin_country;
  }
}
