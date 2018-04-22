package com.netinfo.controller;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.netinfo.entity.Result;
import com.netinfo.entity.StRangeCityEntity;
import com.netinfo.entity.StRangeDistrictEntity;
import com.netinfo.entity.StRangeProvinceEntity;
import com.netinfo.entity.SysLayerEntity;
import com.netinfo.entity.SysRangeEntity;
import com.netinfo.entity.SysResourceEntity;
import com.netinfo.entity.SysRoleEntity;
import com.netinfo.entity.SysRolesLayerEntity;
import com.netinfo.entity.SysRolesResourceEntity;
import com.netinfo.entity.SysUserEntity;
import com.netinfo.entity.SysUsersRangeEntity;
import com.netinfo.entity.SysUsersRoleEntity;
import com.netinfo.entity.ViewUserLayerEntity;
import com.netinfo.entity.ViewUserResourceEntity;
import com.netinfo.service.SysEnviService;
import com.netinfo.service.SysLayerService;
import com.netinfo.service.SysRangeService;
import com.netinfo.service.SysResourceService;
import com.netinfo.service.SysRoleService;
import com.netinfo.service.SysStRangeService;
import com.netinfo.service.SysToolItemService;
import com.netinfo.service.SysUserService;
import com.netinfo.utils.ResultUtil;

import io.jsonwebtoken.Jwts;

@RestController
@RequestMapping("/api")
public class SysAdminController {

	private final static Logger logger = LoggerFactory.getLogger(SysAdminController.class);
	
	@Value("${jwt.tokenName}")
	private String tokenName;
	    
	@Value("${jwt.tokenHead}")
	private String tokenHead;

	@Autowired
	private SysEnviService sysEnviService;
	
	@Autowired
	private SysUserService sysUserService;

	@Autowired
	private SysRoleService sysRoleService;
	
	@Autowired
	private SysLayerService sysLayerService;
	
	@Autowired
	private SysResourceService sysResourceService;
	
	@Autowired
	private SysRangeService sysRangeService;
	
	@Autowired
	private SysToolItemService sysToolItemService;
	
	@Autowired
	private SysStRangeService sysStRangeService;
	
    @Value("${jwt.secret}")
    private String secret;
	
	private BCryptPasswordEncoder  encoder =new BCryptPasswordEncoder();
	
	
	@GetMapping(value = "/getCasInfo")
	public Result<Map<String, String>> getCasInfo(HttpServletRequest request) {
		Map<String, String> tMap = new HashMap<String, String>();
		//String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();
		
		
		return ResultUtil.success(tMap);
	}
	
	@GetMapping(value = "/curUserInfo")
	public Result<Map<String, String>> getCurrentUserInfo(HttpServletRequest request) {
		Map<String, String> tMap = new HashMap<String, String>();
		//String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();
		
		String token = request.getHeader(this.tokenName);
		if (!StringUtils.isEmpty(token) && token.startsWith(this.tokenHead)) {
			token = token.substring(this.tokenHead.length());
        } else {
            // 不按规范,不允许通过验证
        	token = null;
        }
		String currentUser = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
		
		//logger.info("currentUser=="+currentUser);
		SysUserEntity user = this.sysEnviService.getUserById(currentUser);
		//logger.info("user=="+user);
		tMap.put("username", user.getUserId());
		tMap.put("usercnname", user.getUserCnname());
		tMap.put("taskid", user.getCtaskId().toString());
		
		return ResultUtil.success(tMap);
	}
	
	private SysUserEntity  getCuser(String username)
	{
		return this.sysEnviService.getUserById(username);
	}
	
	/**
	 * 查询所有
	 * 
	 * @return
	 */
	@GetMapping(value = "/users")
	public Result<Page<SysUserEntity>> userList(HttpServletRequest request) {
		logger.info("userList");
		int pageNumber = 0;
		int pageSize = 8;

		try {
			pageNumber = Integer.parseInt(request.getParameter("page"));
			if (pageNumber > 0)
				pageNumber = pageNumber - 1;
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			pageNumber = 0;
		}

		String sortType = "auto";
		Sort sort = null;
		if ("auto".equals(sortType)) {
			sort = new Sort(Direction.ASC, "userId");
		} else if ("desc".equals(sortType)) {
			sort = new Sort(Direction.DESC, "userId");
		}

		PageRequest pageInfo = new PageRequest(pageNumber, pageSize, sort);
		Page<SysUserEntity> mList = this.sysUserService.getAll(pageInfo);
		return ResultUtil.success(mList);
	}
	
	@GetMapping(value = "/usermenus")
	public Result<List<Map<String, Object>>> getUserMenus( HttpServletRequest request ) throws Exception {
		//String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();
		List<Map<String, Object>> list = new LinkedList<Map<String, Object>>();
		String token = request.getHeader(this.tokenName);
		if (!StringUtils.isEmpty(token) && token.startsWith(this.tokenHead)) {
			token = token.substring(this.tokenHead.length());
        } else {
            // 不按规范,不允许通过验证
        	token = null;
        	return ResultUtil.success(list);
        }
		String currentUser = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
		
		logger.info("currentUser is "+ currentUser);
		List<ViewUserResourceEntity> resList =  this.sysResourceService.getUserResourceList(currentUser);
		 
		try {
			Map<String, Object> tMap = new HashMap<String, Object>();
			for (ViewUserResourceEntity eachList : resList) {
				
				Map<String, Object> regMap = new HashMap<String, Object>();
				regMap.put("id", eachList.getId().getResourceId());
				regMap.put("name", eachList.getResourceDesc());
				regMap.put("pathstr", eachList.getResourcePath());
				regMap.put("loadstr", eachList.getResourceLoadstr());
				list.add(regMap);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResultUtil.success(list);
	}
	
	@GetMapping(value = "/users/getRoleList/{userId}")
	public Result<List<Map<String, Object>>> getRoleListByUser(@PathVariable("userId") String userId) throws Exception {
		
		List<Map<String, Object>> list = new LinkedList<Map<String, Object>>();
		
		List<SysUsersRoleEntity> checkRoleList =  this.sysUserService.getRoleList(userId);
		 
		try {
			Map<String, Object> tMap = new HashMap<String, Object>();
			Iterable<SysRoleEntity> mList = this.sysRoleService.getAll();
			for (SysRoleEntity eachList : mList) {
				boolean tag = false;
				for(SysUsersRoleEntity  tmprl : checkRoleList)
				{
					if(eachList.getRoleId().equals(tmprl.getRoleId()))
					{
						tag = true;
						break;
					}
				}
				
				Map<String, Object> regMap = new HashMap<String, Object>();
				regMap.put("text", eachList.getRoleDesc());
				regMap.put("value", eachList.getRoleId());
				regMap.put("checked", tag);
				list.add(regMap);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return ResultUtil.success(list);
	}
	
	@GetMapping(value = "/users/getRangeList/{userId}")
	public Result<List<Map<String, Object>>> getRangeListByUser(@PathVariable("userId") String userId) throws Exception {
		
		List<Map<String, Object>> list = new LinkedList<Map<String, Object>>();
		
		List<SysUsersRangeEntity> checkRangeList =  this.sysUserService.getRangeList(userId);
		 
		try {
			Map<String, Object> tMap = new HashMap<String, Object>();
			Iterable<SysRangeEntity> mList = this.sysRangeService.getAllRange();
			for (SysRangeEntity eachList : mList) {
				boolean tag = false;
				for(SysUsersRangeEntity  tmprl : checkRangeList)
				{
					if(eachList.getRangeId().equals(tmprl.getRangeId()))
					{
						tag = true;
						break;
					}
				}
				
				Map<String, Object> regMap = new HashMap<String, Object>();
				regMap.put("text", eachList.getRangeName());
				regMap.put("value", eachList.getRangeId());
				regMap.put("checked", tag);
				list.add(regMap);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return ResultUtil.success(list);
	}

	@Transactional
	@PostMapping(value = "/saveUser")
	public Result<SysUserEntity> saveUser(@RequestBody @Valid SysUserEntity user, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return ResultUtil.error(1, bindingResult.getFieldError().getDefaultMessage());
		}
		SysUserEntity realUser = this.sysUserService.findOne(user.getUserId());
		if (realUser == null)
		{
			realUser = new SysUserEntity();
		}
		realUser.setUserId(user.getUserId());
		realUser.setUserCnname(user.getUserCnname());
		if(! user.getUserPassword().equalsIgnoreCase(realUser.getUserPassword()) )
			realUser.setUserPassword(encoder.encode(user.getUserPassword()));
		//this.sysUserService.deleteRoleByUserId(user);
		//this.sysUserService.saveUserRoleRelate(new SysUsersRoleEntity(user.getUserId(),user.getRoleId()));
		

		//this.sysUserService.deleteRangeByUserId(user);
		//this.sysUserService.saveUserRangeRelate(new SysUsersRangeEntity(user.getUserId(),user.getRange().getRangeId()));
		return ResultUtil.success(this.sysUserService.save(realUser));
	}
	
	@PostMapping(value = "/deleteUser")
	public Result deleteUser(@RequestBody @Valid SysUserEntity user, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return ResultUtil.error(1, bindingResult.getFieldError().getDefaultMessage());
		} 
		this.sysUserService.delete(user);
		return ResultUtil.success();
	}
	/**
	 * @return
	 */
	@PostMapping(value = "/test_saveuser")
	public Result<SysUserEntity> userAdd(@Valid SysUserEntity user, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return ResultUtil.error(1, bindingResult.getFieldError().getDefaultMessage());
		}
		return ResultUtil.success(sysUserService.save(user));
	}

	// 查询
	@GetMapping(value = "/users/{id}")
	public Result<SysUserEntity> userFindOne(@PathVariable("id") String userId) {
		return ResultUtil.success(sysUserService.findOne(userId));
	}

	// 更新
	@PutMapping(value = "/users/{id}")
	public SysUserEntity userUpdate(@PathVariable("id") String id, @RequestParam("userCnname") String userCnname,
			@RequestParam("userPassword") String userPassword) {
		SysUserEntity user = new SysUserEntity();
		user.setUserId(id);
		user.setUserCnname(userCnname);
		user.setUserPassword(userPassword);
		return sysUserService.save(user);
	}

	// 删除
	@DeleteMapping(value = "/users/{id}")
	public void userDelete(@PathVariable("id") String id) {
		sysUserService.delete(id);
	}

	@GetMapping(value = "/users/cnname/{cnname}")
	public List<SysUserEntity> userListByAge(@PathVariable("cnname") String cnname) {
		return sysUserService.findByUserCnname(cnname);
	}

	@PostMapping(value = "/users/two")
	public void userTwo() {
		sysUserService.insertTwo();
	}

	@GetMapping(value = "users/getEnabled/{id}")
	public void getEnabled(@PathVariable("id") String userId) throws Exception {
		sysUserService.getEnabled(userId);
	}
	

	@GetMapping(value = "/roles")
	public Result<Page<SysRoleEntity>> roleList(HttpServletRequest request) {
		logger.info("roleList");
		int pageNumber = 0;
		int pageSize = 8;
		String searchText = request.getParameter("searchText");

		try {
			pageNumber = Integer.parseInt(request.getParameter("page")) - 1;
			if (pageNumber < 0)
				pageNumber = 0;
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			pageNumber = 0;
		}

		String sortType = "auto";
		Sort sort = null;
		if ("auto".equals(sortType)) {
			sort = new Sort(Direction.ASC, "roleId");
		} else if ("desc".equals(sortType)) {
			sort = new Sort(Direction.DESC, "roleId");
		}

		PageRequest pageInfo = new PageRequest(pageNumber, pageSize, sort);

		Page<SysRoleEntity> mList = this.sysRoleService.getRoleList(pageInfo);

		return ResultUtil.success(mList);
	}

	@GetMapping(value = "/roles/getLayerList/{roleId}")
	public Result<List<Map<String, Object>>> getLayerListByRole(@PathVariable("roleId") String roleId) throws Exception {
		
		List<Map<String, Object>> list = new LinkedList<Map<String, Object>>();
		
		List<SysRolesLayerEntity> checkLayerList =  this.sysRoleService.getRoleLayerList(roleId);
		 
		try {
			Map<String, Object> tMap = new HashMap<String, Object>();
			Iterable<SysLayerEntity> mList = this.sysLayerService.getAllLayer();
			for (SysLayerEntity eachList : mList) {
				boolean tag = false;
				for(SysRolesLayerEntity  tmprl : checkLayerList)
				{
					if(eachList.getLayerId().equals(tmprl.getLayerId()))
					{
						tag = true;
						break;
					}
				}
				
				Map<String, Object> regMap = new HashMap<String, Object>();
				regMap.put("text", eachList.getLayername());
				regMap.put("value", eachList.getLayerId());
				regMap.put("checked", tag);
				list.add(regMap);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return ResultUtil.success(list);
	}
	
	@GetMapping(value = "/roles/getMenuList/{roleId}")
	public Result<List<Map<String, Object>>> getMenuListByRole(@PathVariable("roleId") String roleId) throws Exception {
		
		List<Map<String, Object>> list = new LinkedList<Map<String, Object>>();
		
		List<SysRolesResourceEntity> checkLayerList =  this.sysRoleService.getRoleMenuList(roleId);
		 
		try {
			Map<String, Object> tMap = new HashMap<String, Object>();
			Iterable<SysResourceEntity> mList = this.sysResourceService.getAllMenu();
			for (SysResourceEntity eachList : mList) {
				boolean tag = false;
				for(SysRolesResourceEntity  tmprl : checkLayerList)
				{
					if(eachList.getResourceId().equals(tmprl.getResourceId()))
					{
						tag = true;
						break;
					}
				}
				
				Map<String, Object> regMap = new HashMap<String, Object>();
				regMap.put("text", eachList.getResourceDesc());
				regMap.put("value", eachList.getResourceId());
				regMap.put("checked", tag);
				list.add(regMap);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return ResultUtil.success(list);
	}
	
	
	@Transactional
	@PostMapping(value = "/saveRole")
	public Result<SysRoleEntity> saveRole(@RequestBody @Valid SysRoleEntity role, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return ResultUtil.error(1, bindingResult.getFieldError().getDefaultMessage());
		}
		this.sysRoleService.deleteLayerByRoleId(role);
		for (String layerId : role.getLayerIds())
		{
			this.logger.info("layerId==="+layerId);
			
			this.sysRoleService.saveRoleLayerRelate(new SysRolesLayerEntity(role.getRoleId(),layerId));
		}
		this.sysRoleService.deleteMenuByRoleId(role);
		for (String menuId : role.getMenuIds())
		{
			this.logger.info("menuId==="+menuId);
			
			this.sysRoleService.saveRoleResourceRelate(new SysRolesResourceEntity(role.getRoleId(),menuId));
		}
		return ResultUtil.success(this.sysRoleService.save(role));
	}
	
	@PostMapping(value = "/deleteRole")
	public Result deleteRole(@RequestBody @Valid SysRoleEntity role, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return ResultUtil.error(1, bindingResult.getFieldError().getDefaultMessage());
		}
		this.sysRoleService.delete(role);
		return ResultUtil.success();
	}
	
	@GetMapping(value = "stRangeList/{proId}/{cityId}")
	public Result<List<Map<String, Object>>> getStRangeList(@PathVariable("proId") Long proId,@PathVariable("cityId") Long cityId) throws Exception {
		Map<String, Object> districtMap = new HashMap<String, Object>();
		List<Map<String, Object>> dlist = new LinkedList<Map<String, Object>>();
		List<StRangeDistrictEntity> districtList = this.sysStRangeService.getDistrictListByCityId(cityId);
		for(StRangeDistrictEntity stDistrict : districtList)
		{
			districtMap =  new HashMap<String, Object>();
			districtMap.put("label", stDistrict.getNamechn());
			districtMap.put("data", stDistrict.getObjectid());
			districtMap.put("icon", "fa-file-image-o");
			districtMap.put("grade", 3);
			districtMap.put("pid", cityId);
			districtMap.put("selectable", true);
			districtMap.put("leaf", true);
			dlist.add(districtMap);
		}
		return ResultUtil.success(dlist);
	}
	
	
	@GetMapping(value = "stRangeList/{proId}")
	public Result<List<Map<String, Object>>> getStRangeList(@PathVariable("proId") Long proId) throws Exception {
		Map<String, Object> cityMap = new HashMap<String, Object>();
		List<Map<String, Object>> clist = new LinkedList<Map<String, Object>>();
		List<StRangeCityEntity> cityList = this.sysStRangeService.getCityListByProvinceId(proId);
		for(StRangeCityEntity stCity : cityList)
		{
			cityMap =  new HashMap<String, Object>();
			cityMap.put("label", stCity.getNamechn());
			cityMap.put("data", stCity.getObjectid());
			cityMap.put("expandedIcon", "fa-folder-open");
			cityMap.put("collapsedIcon", "fa-folder");
			cityMap.put("grade", 2);
			cityMap.put("pid", proId);
			cityMap.put("leaf", false);
			cityMap.put("selectable", true);
			clist.add(cityMap);
		}
		return ResultUtil.success(clist);
	}
	private List<Map<String, Object>> provList = new LinkedList<Map<String, Object>>();
	
	@GetMapping(value = "/stRangeList")
	public Result<List<Map<String, Object>>> getStRangeList(HttpServletRequest request) {
		//String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();
		List<Map<String, Object>> list = new LinkedList<Map<String, Object>>();

		Map<String, Object> priovinceMap = new HashMap<String, Object>();
		String term = request.getParameter("term");
		logger.info("stRangeList");

		Iterable<StRangeProvinceEntity> mList = new LinkedList<StRangeProvinceEntity>();
				
		if(term == null || term.trim().length()<1) //无过滤项
		{
			mList = this.sysStRangeService.getAllProvince();
			for(StRangeProvinceEntity stProvince : mList)
			{

				priovinceMap = new HashMap<String, Object>();
				priovinceMap.put("label", stProvince.getNamechn());
				priovinceMap.put("data", stProvince.getObjectid());
				priovinceMap.put("expandedIcon", "fa-folder-open");
				priovinceMap.put("collapsedIcon", "fa-folder");
				priovinceMap.put("grade", 1);
				priovinceMap.put("pid", 0);
				priovinceMap.put("selectable", true);
				priovinceMap.put("leaf", false);
				list.add(priovinceMap);
			}
			return ResultUtil.success(list);
		}	
		else //有过滤条件
		{
			this.provList.clear();
			mList = this.sysStRangeService.filterByName(term);
			for(StRangeProvinceEntity stProvince : mList)
			{
				Map<String, Object> provMap = new HashMap<String, Object>();
				provMap.put("label", stProvince.getNamechn());
				provMap.put("data", stProvince.getObjectid());
				provMap.put("expandedIcon", "fa-folder-open");
				provMap.put("collapsedIcon", "fa-folder");
				provMap.put("grade", 1);
				provMap.put("pid", 0);
				provMap.put("selectable", true);
				provMap.put("leaf", false);
				this.provList.add(provMap);
			}
			getStRangeListByTerm(term);
				
			return ResultUtil.success(this.provList);
		}
	}
	
	private Map<String, Object> getProviceList(Long id)
	{
		if(this.provList.size()>0)
		{
			for(Map<String, Object> pMap:this.provList)
			{
				if(pMap.get("data").equals(id)){
					if(pMap.containsKey("children")==false || pMap.get("children") == null)
					{
						pMap.put("children", new LinkedList<Map<String, Object>>());
					}
					if(pMap.containsKey("expanded")==false )
						pMap.put("expanded", true);
					if( pMap.containsKey("expanded") && (boolean)pMap.get("expanded") == false)
					{
						pMap.remove("expanded");
						pMap.put("expanded", true);
					}
					return pMap;
				}
			}
		}
		StRangeProvinceEntity prov = this.sysStRangeService.getProviceById(id);
		Map<String, Object> priovinceMap = new HashMap<String, Object>();
		priovinceMap.put("label", prov.getNamechn());
		priovinceMap.put("data", prov.getObjectid());
		priovinceMap.put("expandedIcon", "fa-folder-open");
		priovinceMap.put("collapsedIcon", "fa-folder");
		priovinceMap.put("grade", 1);
		priovinceMap.put("pid", 0);
		priovinceMap.put("selectable", true);
		priovinceMap.put("expanded", true);
		priovinceMap.put("children", new LinkedList<Map<String, Object>>());
		priovinceMap.put("leaf", false);
		this.provList.add(priovinceMap);
		return priovinceMap;
	}
	
	private Map<String, Object> getProviceListByCity(Long id)
	{
		if(this.provList.size()>0)
		{
			for(Map<String, Object> pMap:this.provList)
			{
				List<Map<String, Object>> cityList = ((List<Map<String, Object>>)(pMap.get("children")));
				if (cityList!= null)
				{
					for(Map<String, Object> cMap:cityList)
					{
						if(cMap.get("data").equals(id))
						{
							if(cMap.containsKey("children")==false || cMap.get("children") == null)
							{
								cMap.put("children", new LinkedList<Map<String, Object>>());
							}
							if(cMap.containsKey("expanded")==false )
								cMap.put("expanded", true);
							if( cMap.containsKey("expanded") && (boolean)cMap.get("expanded") == false)
							{
								cMap.remove("expanded");
								cMap.put("expanded", true);
							}
							return cMap;
						}
					}
				}
			}
		}
		StRangeCityEntity city = this.sysStRangeService.getCityById(id);
		Map<String, Object> cityMap = new HashMap<String, Object>();
		cityMap =  new HashMap<String, Object>();
		cityMap.put("label", city.getNamechn());
		cityMap.put("data", city.getObjectid());
		cityMap.put("expandedIcon", "fa-folder-open");
		cityMap.put("collapsedIcon", "fa-folder");
		cityMap.put("grade", 2);
		cityMap.put("pid", city.getPid());
		cityMap.put("expanded", true);
		cityMap.put("children", new LinkedList<Map<String, Object>>());
		cityMap.put("leaf", false);


		if(this.provList.size()>0)
		{
			for(Map<String, Object> pMap:this.provList)
			{
				if(pMap.get("data").equals(city.getPid())){
					if(pMap.containsKey("children")==false || pMap.get("children") == null)
					{
						pMap.put("children", new LinkedList<Map<String, Object>>());
					}
					if(pMap.containsKey("expanded")==false )
						pMap.put("expanded", true);
					if( pMap.containsKey("expanded") && (boolean)pMap.get("expanded") == false)
					{
						pMap.remove("expanded");
						pMap.put("expanded", true);
					}
					
					((List<Map<String, Object>>)(pMap.get("children"))).add(cityMap);
					return cityMap;
				}
			}
		}
		
		StRangeProvinceEntity prov = this.sysStRangeService.getProviceById(city.getPid());
		Map<String, Object> priovinceMap = new HashMap<String, Object>();
		priovinceMap.put("label", prov.getNamechn());
		priovinceMap.put("data", prov.getObjectid());
		priovinceMap.put("expandedIcon", "fa-folder-open");
		priovinceMap.put("collapsedIcon", "fa-folder");
		priovinceMap.put("grade", 1);
		priovinceMap.put("pid", 0);
		priovinceMap.put("selectable", true);
		priovinceMap.put("expanded", true);
		priovinceMap.put("children", new LinkedList<Map<String, Object>>());
		priovinceMap.put("leaf", false);
		((List<Map<String, Object>>)(priovinceMap.get("children"))).add(cityMap);
		this.provList.add(priovinceMap);
		return cityMap;
	}
	
	
	public List<Map<String, Object>> getStRangeListByTerm(String term) {
		Map<String, Object> cityMap = new HashMap<String, Object>();
		List<Map<String, Object>> clist = new LinkedList<Map<String, Object>>();
		Iterable<StRangeCityEntity> cityList = this.sysStRangeService.getCityListFilterByTerm(term);
		if(cityList.iterator().hasNext())
		{
			for(StRangeCityEntity stCity : cityList)
			{
				cityMap =  new HashMap<String, Object>();
				cityMap.put("label", stCity.getNamechn());
				cityMap.put("data", stCity.getObjectid());
				cityMap.put("expandedIcon", "fa-folder-open");
				cityMap.put("collapsedIcon", "fa-folder");
				cityMap.put("grade", 2);
				cityMap.put("pid", stCity.getPid());
				cityMap.put("leaf", false);
				cityMap.put("selectable", true);
				
				Map<String, Object> promMap =  getProviceList(stCity.getPid());
				((List<Map<String, Object>>)(promMap.get("children"))).add(cityMap);
			}
		}

		Iterable<StRangeDistrictEntity> districtList = this.sysStRangeService.getDistrictListFilterByTerm(term);
		if(districtList.iterator().hasNext())
		{
			Map<String, Object> districtMap = new HashMap<String, Object>();
			List<Map<String, Object>> dlist = new LinkedList<Map<String, Object>>();
			for(StRangeDistrictEntity stDistrict : districtList)
			{
				districtMap =  new HashMap<String, Object>();
				districtMap.put("label", stDistrict.getNamechn());
				districtMap.put("data", stDistrict.getObjectid());
				districtMap.put("icon", "fa-file-image-o");
				districtMap.put("grade", 3);
				districtMap.put("pid", stDistrict.getPid());
				districtMap.put("selectable", true);
				districtMap.put("leaf", true);
				
				Map<String, Object> cMap = getProviceListByCity(stDistrict.getPid());
				((List<Map<String, Object>>)(cMap.get("children"))).add(districtMap);
			}
		}
		
		return clist;
	}
	
	
	@GetMapping(value = "/getBaseLayer//{layerId}")
	public Result<List<Map<String, Object>>> getGroundLayerList(@PathVariable("layerId") String layerId) {
		List<Map<String, Object>> list = new LinkedList<Map<String, Object>>();
		Map<String, Object> tmpMap = new HashMap<String, Object>();
		Map<String, Object> layerMap = new HashMap<String, Object>();
		logger.info("groundLayer");

		SysLayerEntity mLayer = this.sysLayerService.findOne(layerId);
		if(mLayer!= null)
		{
			tmpMap = new HashMap<String, Object>();
			layerMap = new HashMap<String, Object>();
			layerMap.put("title", mLayer.getLayername());
			layerMap.put("id", mLayer.getLayerId());
			layerMap.put("url", mLayer.getDatasource());
			layerMap.put("opacity", mLayer.getOpacity());
			layerMap.put("listMode", mLayer.getListmode());
			layerMap.put("maxScale", mLayer.getMaxscale());
			layerMap.put("minScale", mLayer.getMinscale());
			layerMap.put("visible", mLayer.getInitvisible()==0?false:true);
			
			tmpMap.put("label", mLayer.getLayername());
			tmpMap.put("type", mLayer.getLayertype());
			tmpMap.put("config", layerMap);
			list.add(tmpMap);
		}
		return ResultUtil.success(list);
	}
	
	
	@GetMapping(value = "/userlayers")
	public Result<List<Map<String, Object>>> getUserlayerList(HttpServletRequest request) {
		//String currentUser =  request.getParameter("cUser");
		String token = request.getHeader(this.tokenName);
		if (!StringUtils.isEmpty(token) && token.startsWith(this.tokenHead)) {
			token = token.substring(this.tokenHead.length());
        } else {
            // 不按规范,不允许通过验证
        	token = null;
        }
		String currentUser = Jwts.parser()
				.setSigningKey(secret)
				.parseClaimsJws(token)
				.getBody()
				.getSubject();
		
		List<Map<String, Object>> list = new LinkedList<Map<String, Object>>();
		Map<String, Object> tmpMap = new HashMap<String, Object>();
		Map<String, Object> layerMap = new HashMap<String, Object>();
		logger.info("userlayers");
		Long taskId = this.getCuser(currentUser).getCtaskId();
		List<ViewUserLayerEntity> mList = this.sysLayerService.getUserLayer(currentUser);
		for(ViewUserLayerEntity tUserLayer : mList)
		{
			tmpMap = new HashMap<String, Object>();
			layerMap = new HashMap<String, Object>();
			layerMap.put("title", tUserLayer.getLayername());
			layerMap.put("id", tUserLayer.getId().getLayerId());
			layerMap.put("url", tUserLayer.getDatasource());
			layerMap.put("opacity", tUserLayer.getOpacity());
			List fields  = new LinkedList<String>();
			fields.add("*");
			layerMap.put("outFields", fields);
			//layerMap.put("definitionExpression", tUserLayer.getDefexpression());
			layerMap.put("definitionExpression", "TASK_ID = "+ taskId);
			Map<String, Object> modeMap = new HashMap<String, Object>();
			modeMap.put("mode", tUserLayer.getElevationinfo());
			modeMap.put("offset","1");
			layerMap.put("elevationInfo", modeMap);
			layerMap.put("editfields", this.sysLayerService.getLayerFieldList(tUserLayer.getId().getLayerId()));
			layerMap.put("labelsVisible", tUserLayer.getLabelsvisible()==1?true:false);
			layerMap.put("labelingInfo", tUserLayer.getLabelinginfo());
			layerMap.put("listMode", tUserLayer.getListmode());
			layerMap.put("maxScale", tUserLayer.getMaxscale());
			layerMap.put("minScale", tUserLayer.getMinscale());
			layerMap.put("visible", tUserLayer.getInitvisible()==0?false:true);
			layerMap.put("geometryType", tUserLayer.getGeomtype());
			//layerMap.put("returnZ", "true");
			
			tmpMap.put("label", tUserLayer.getLayername());
			tmpMap.put("type", tUserLayer.getLayertype());
			tmpMap.put("config", layerMap);
			tmpMap.put("tools", this.sysToolItemService.findByLayerid(tUserLayer.getId().getLayerId()));
			list.add(tmpMap);
		}
		return ResultUtil.success(list);
	}

	@GetMapping(value = "/layers")
	public Result<Page<SysLayerEntity>> getlayerList(HttpServletRequest request) {
		//String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();
		String token = request.getHeader(this.tokenName);
		if (!StringUtils.isEmpty(token) && token.startsWith(this.tokenHead)) {
			token = token.substring(this.tokenHead.length());
        } else {
            // 不按规范,不允许通过验证
        	token = null;
        }
		String currentUser = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
		logger.info("layerList");
		int pageNumber = 0;
		int pageSize = 8;
		String searchText = request.getParameter("searchText");

		try {
			pageNumber = Integer.parseInt(request.getParameter("page")) - 1;
			if (pageNumber < 0)
				pageNumber = 0;
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			pageNumber = 0;
		}

		String sortType = "auto";
		Sort sort = null;
		if ("auto".equals(sortType)) {
			sort = new Sort(Direction.ASC, "seq");
		} else if ("desc".equals(sortType)) {
			sort = new Sort(Direction.DESC, "layerId");
		}

		PageRequest pageInfo = new PageRequest(pageNumber, pageSize, sort);

		Page<SysLayerEntity> mList = this.sysLayerService.getAllLayer(pageInfo);

		return ResultUtil.success(mList);
	}
	
	@PostMapping(value = "/saveLayer")
	public Result<SysLayerEntity> saveLayer(@RequestBody @Valid SysLayerEntity layer, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return ResultUtil.error(1, bindingResult.getFieldError().getDefaultMessage());
		}
		return ResultUtil.success(this.sysLayerService.save(layer));
	}
	@GetMapping(value = "/layers/{id}")
	public Result<SysLayerEntity> layerFindOne(@PathVariable("id") String layerId) {
		return ResultUtil.success(sysLayerService.findOne(layerId));
	}
	@PostMapping(value = "/deleteLayer")
	public Result deleteLayer(@RequestBody @Valid SysLayerEntity layer, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return ResultUtil.error(1, bindingResult.getFieldError().getDefaultMessage());
		}
		this.sysLayerService.delete(layer);
		return ResultUtil.success();
	}
	
	@GetMapping(value = "/menus")
	public Result<Page<SysLayerEntity>> getMenuList(HttpServletRequest request) {
		logger.info("getMenuList");
		int pageNumber = 0;
		int pageSize = 8;
		String searchText = request.getParameter("searchText");

		try {
			pageNumber = Integer.parseInt(request.getParameter("page")) - 1;
			if (pageNumber < 0)
				pageNumber = 0;
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			pageNumber = 0;
		}

		String sortType = "auto";
		Sort sort = null;
		if ("auto".equals(sortType)) {
			sort = new Sort(Direction.ASC, "resourceId");
		} else if ("desc".equals(sortType)) {
			sort = new Sort(Direction.DESC, "resourceId");
		}

		PageRequest pageInfo = new PageRequest(pageNumber, pageSize, sort);

		Page<SysResourceEntity> mList = this.sysResourceService.getResourceList(pageInfo);

		return ResultUtil.success(mList);
	}
	
	@PostMapping(value = "/saveMenu")
	public Result<SysLayerEntity> saveMenu(@RequestBody @Valid SysResourceEntity resource, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return ResultUtil.error(1, bindingResult.getFieldError().getDefaultMessage());
		}
		return ResultUtil.success(this.sysResourceService.save(resource));
	}
	@GetMapping(value = "/menus/{id}")
	public Result<SysResourceEntity> menuFindOne(@PathVariable("id") String resourceId) {
		return ResultUtil.success(sysResourceService.findById(resourceId));
	}
	@PostMapping(value = "/deleteMenu")
	public Result deleteMenu(@RequestBody @Valid SysResourceEntity menu, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return ResultUtil.error(1, bindingResult.getFieldError().getDefaultMessage());
		}
		this.sysResourceService.delete(menu);
		return ResultUtil.success();
	}
}
