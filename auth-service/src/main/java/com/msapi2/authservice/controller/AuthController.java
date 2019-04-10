package com.msapi2.authservice.controller;

import com.msapi2.authservice.model.GenericResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RestController
@RequestMapping(value = "authentication")
@Api(value = "/authentication")
public class AuthController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private ConsumerTokenServices tokenServices;

    @DeleteMapping(value = "/revokeToken/{tokenId:.+}")
    @ApiOperation(value = "Revoke Token", httpMethod = "DELETE")
    public ResponseEntity<GenericResponse> revokeToken(
        @PathVariable("tokenId")
        @ApiParam(value = "Token Id") String tokenId
    ) {

        LOGGER.info("AUTHENTICATION [CONTROLLER] - DELETE Revoke Token id: " + tokenId + " ! ");

        GenericResponse response = new GenericResponse();
        try {
            if (this.tokenServices.revokeToken(tokenId)) {
                return ResponseEntity.ok(response);
            } else {
                response.setStatus(BAD_REQUEST.toString());
                return ResponseEntity.badRequest().body(response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(INTERNAL_SERVER_ERROR.toString());
            response.setMessage(e.getMessage());
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
