package it.unical.classroommanager_api.configuration.i18n;

import io.micrometer.common.util.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import java.util.List;
import java.util.Locale;

@Component
public class LanguageResolver extends AcceptHeaderLocaleResolver {

  /* Quando il server riceve una richiesta HTTP, la richiesta passa anche da qui. Qui viene fatto un controllo sull' Header.
     In particolare si controlla il campo "Accept-Language" */
  @Override
  public Locale resolveLocale(HttpServletRequest request) {
    String language = request.getHeader("Accept-Language");
    List<Locale> supportedLocales = getSupportedLocales();
    Locale defaultLocale = getDefaultLocale();

    if (StringUtils.isEmpty(language)) {
      return defaultLocale;
    }
    Locale requestLocale = Locale.forLanguageTag(language);
    if (supportedLocales.contains(requestLocale)) {
      return requestLocale;
    } else {
      return defaultLocale;
    }
  }

}