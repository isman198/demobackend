import React from 'react';
import { DropdownItem } from 'reactstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { NavLink as Link } from 'react-router-dom';
import { NavDropdown } from '../header-components';

export const EntitiesMenu = props => (
  // tslint:disable-next-line:jsx-self-close
  <NavDropdown icon="th-list" name="Entities" id="entity-menu">
    <DropdownItem tag={Link} to="/entity/efek-my-suffix">
      <FontAwesomeIcon icon="asterisk" fixedWidth />
      &nbsp;Efek My Suffix
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/harga-penutupan-my-suffix">
      <FontAwesomeIcon icon="asterisk" fixedWidth />
      &nbsp;Harga Penutupan My Suffix
    </DropdownItem>
    {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
  </NavDropdown>
);
