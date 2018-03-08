package gov.usgs.wim.wdnr.webservice;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import gov.usgs.wim.wdnr.dao.SanitaryDataDao;
import gov.usgs.wim.wdnr.domain.SanitaryData;
import gov.usgs.wim.wdnr.domain.Views;
import io.swagger.annotations.Api;

@Api(tags={"Sanitary Data"})
@RestController
@RequestMapping("/sanitaryData")
public class SanitaryDataController {

    @Autowired
    private SanitaryDataDao sDao;
    @Autowired
    private Validator validator;


    public static final String UNKNOWN_USERNAME = "unknown ";


//    @PreAuthorize("hasPermission(#sd, null)")
    @PostMapping()
    @JsonView(Views.Response.class)
    public List<SanitaryData> createSanitaryData(@RequestBody List<SanitaryData> sd, HttpServletResponse response) throws IOException {
//        sd.setCreatedBy(getUsername());//?
//        sd.setUpdatedBy(getUsername());//?
        boolean noErrors = true;
        for (int i = 0; i < sd.size(); i++) {
            Set<ConstraintViolation<SanitaryData>> errors = validator.validate(sd.get(i));
            sd.get(i).setValidationErrors(errors);
            if (!errors.isEmpty()) {
                noErrors = false;
//            return sDao.getById(newId); //? //ONLY NEEDED IF we are going to echo back data-- Ask Alice
            } else {
                response.setStatus(400);
//
            }
        }
        if (noErrors) {
            for (int i = 0; i < sd.size(); i++) {
                String newId = sDao.create(sd.get(i));
                response.setStatus(HttpStatus.CREATED.value());
            }
        }

        return sd;
    }


//    protected String getUsername() { //ask alice if we are going to get username / save any of this information
//        String username = UNKNOWN_USERNAME;
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (null != authentication && !(authentication instanceof AnonymousAuthenticationToken)) {
//            username= authentication.getName();
//        }
//        return username;
//    }

}