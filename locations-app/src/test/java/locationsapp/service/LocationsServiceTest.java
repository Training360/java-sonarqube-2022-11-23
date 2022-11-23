package locationsapp.service;

import locationsapp.dto.CreateLocationCommand;
import locationsapp.repository.LocationsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class LocationsServiceTest {

    @Mock
    LocationsRepository locationsRepository;

    ModelMapper modelMapper = new ModelMapper();

    LocationsService locationsService;

    @BeforeEach
    void init() {
        locationsService = new LocationsService(locationsRepository, modelMapper);
    }

    @Test
    void testCreate() {
        var command = new CreateLocationCommand("Home", "1,1");
        var response = locationsService.createLocation(command);

        verify(locationsRepository).save(argThat(l ->
            l.getName().equals("Home") &&
                l.getLat() == 1.0)
        );

        assertEquals("Home", response.getName());
        assertEquals(1.0, response.getLat());
    }


}
