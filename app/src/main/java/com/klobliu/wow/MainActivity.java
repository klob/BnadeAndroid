package com.klobliu.wow;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.diandi.klob.sdk.app.BaseFragmentParam;
import com.diandi.klob.sdk.ui.common.KFragment;
import com.diandi.klob.sdk.util.L;
import com.diandi.klob.sdk.widget.TopBar;
import com.klobliu.wow.data.IParse;
import com.klobliu.wow.db.ItemDao;
import com.klobliu.wow.model.Item;
import com.klobliu.wow.model.ItemData;
import com.klobliu.wow.network.Blade;
import com.klobliu.wow.network.RD;
import com.klobliu.wow.ui.BaseActivity;
import com.klobliu.wow.ui.ItemDataActivity;
import com.klobliu.wow.ui.KActivityTest;
import com.klobliu.wow.ui.RouterActivity;
import com.klobliu.wow.ui.UITestActivity;
import com.klobliu.wow.ui.crash.CrashFragment;
import com.klobliu.wow.ui.hot.HotFragment;
import com.klobliu.wow.ui.love.LoveFragment;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;

public class MainActivity extends BaseActivity implements View.OnClickListener, IParse<ItemData> {

    private static final String TAG = "MainActivity";
    private EditText itemName;
    private Button button;
    private Button hot;
    private Button crash;
    @BindView(R.id.love)
    Button mLoveBtn;
    @BindView(R.id.realm)
    TextView mRealm;

    @Override
    protected int getFragmentContainerId() {
        return R.id.container;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        mRealm.setText(BladeManager.getInstance().getCurrentRealmName());
        mLoveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startAnimActivity(LoveFragment.LoveActivity.class);
            }
        });
        setSwipeBackEnable(false);
        itemName = (EditText) findViewById(R.id.itemName);
        button = (Button) findViewById(R.id.btn);
        hot = (Button) findViewById(R.id.hot);
        crash = (Button) findViewById(R.id.crash);
        crash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startAnimActivity(CrashFragment.CrashActivity.class);
            }
        });
        hot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startAnimActivity(HotFragment.HotActivity.class);
            }
        });
        initTopBarForRight("查询", R.drawable.bg_go_settings, new TopBar.onRightImageButtonClickListener() {
            @Override
            public void onClick() {
                Intent intent = new Intent(mContext, RouterActivity.class);
                intent.putExtra("type", 2);
                startActivityForResult(intent, 101);
                in();
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(itemName.getText().toString())) {
                    ShowToast("物品名不能为空");
                } else {
                    startAnimActivity(ItemDataActivity.class, Item.ITEM_NAME, itemName.getText().toString());
                }

            }
        });
        L.d(TAG, ItemDao.getInstance().queryLoved());
        Blade b = new Blade();
        //Object o = JSON.parseArray("[{\"icon\":\"ability_bossfellord_felfissure\",\"queried\":716,\"name\":\"便携式邪能散播器\",\"id\":140363},{\"icon\":\"inv_qiraj_hiltornate\",\"queried\":662,\"name\":\"残破的剑柄\",\"id\":50380},{\"icon\":\"inv_pants_02\",\"queried\":493,\"name\":\"光荣腿铠\",\"id\":14970},{\"icon\":\"spell_shadow_demoniccircleteleport\",\"queried\":434,\"name\":\"军团勋章\",\"id\":128315},{\"icon\":\"inv_alchemy_potion_03\",\"queried\":392,\"name\":\"敏捷思维药剂\",\"id\":128312},{\"icon\":\"inv_alchemy_potion_06\",\"queried\":383,\"name\":\"时沙之瓶\",\"id\":65891},{\"icon\":\"inv_ghostlycharger\",\"queried\":377,\"name\":\"幽灵军马的颅骨\",\"id\":93671},{\"icon\":\"inv_scroll_04\",\"queried\":348,\"name\":\"设计图：魔钢长剑\",\"id\":23629},{\"icon\":\"inv_shirt_08\",\"queried\":315,\"name\":\"海盗外衣\",\"id\":14175},{\"icon\":\"inv_elemental_spiritofharmony_2\",\"queried\":296,\"name\":\"祥和之灵\",\"id\":76061},{\"icon\":\"inv_tailoring_hexweavebag\",\"queried\":232,\"name\":\"妖纹包\",\"id\":114821},{\"icon\":\"inv_sword_42\",\"queried\":96,\"name\":\"疾速之剑\",\"id\":9424},{\"icon\":\"ability_mount_shreddermount\",\"queried\":91,\"name\":\"飞天魔像\",\"id\":95416},{\"icon\":\"inv_pants_05\",\"queried\":85,\"name\":\"翡翠腿铠\",\"id\":14920},{\"icon\":\"inv_ingot_livingsteel\",\"queried\":84,\"name\":\"活化钢\",\"id\":72104},{\"icon\":\"inv_sword_59\",\"queried\":82,\"name\":\"魔钢长剑\",\"id\":23540},{\"icon\":\"inv_misc_boilingblood\",\"queried\":76,\"name\":\"狂野之血\",\"id\":118472},{\"icon\":\"inv_sword_22\",\"queried\":71,\"name\":\"提布的炽炎长剑\",\"id\":1728},{\"icon\":\"inv_boots_plate_02\",\"queried\":69,\"name\":\"乌鸦卫士护胫\",\"id\":28494},{\"icon\":\"trade_archaeology_dwarf_runestone\",\"queried\":66,\"name\":\"占卜者符文\",\"id\":71716},{\"icon\":\"inv_helmet_119\",\"queried\":64,\"name\":\"上流社会的礼帽\",\"id\":54451},{\"icon\":\"inv_scroll_04\",\"queried\":63,\"name\":\"设计图：氪金圣剑\",\"id\":23630},{\"icon\":\"inv_elemental_primal_fire\",\"queried\":58,\"name\":\"源生火焰\",\"id\":21884},{\"icon\":\"inv_misc_nativebeastfur\",\"queried\":54,\"name\":\"奢华兽毛\",\"id\":111557},{\"icon\":\"inv_chest_plate07\",\"queried\":50,\"name\":\"翡翠胸甲\",\"id\":14915},{\"icon\":\"inv_ore_trilliumwhite\",\"queried\":50,\"name\":\"白色延极矿石\",\"id\":72103},{\"icon\":\"inv_chest_plate03\",\"queried\":50,\"name\":\"光荣胸甲\",\"id\":14966},{\"icon\":\"inv_ore_trilliumblack\",\"queried\":49,\"name\":\"黑色延极矿石\",\"id\":72094},{\"icon\":\"inv_offhand_hyjal_d_01\",\"queried\":48,\"name\":\"幻象之书：艾泽拉斯\",\"id\":138787},{\"icon\":\"inv_enchanting_wod_crystal\",\"queried\":43,\"name\":\"时光水晶\",\"id\":113588},{\"icon\":\"spell_shadow_shadetruesight\",\"queried\":43,\"name\":\"死灵精华\",\"id\":12808},{\"icon\":\"inv_chest_leather_09\",\"queried\":43,\"name\":\"勘察员的胸甲\",\"id\":14562},{\"icon\":\"inv_misc_truegold\",\"queried\":42,\"name\":\"真金\",\"id\":58480},{\"icon\":\"inv_helmet_176\",\"queried\":41,\"name\":\"莱茵石太阳镜\",\"id\":52489},{\"icon\":\"inv_ore_khorium\",\"queried\":41,\"name\":\"氪金矿石\",\"id\":23426},{\"icon\":\"inv_ingot_trillium\",\"queried\":40,\"name\":\"延极锭\",\"id\":72095},{\"icon\":\"ability_mount_blackdirewolf\",\"queried\":40,\"name\":\"夜嚎铁颚狼\",\"id\":116794},{\"icon\":\"inv_misc_rune_09\",\"queried\":40,\"name\":\"冰冻符文\",\"id\":22682},{\"icon\":\"inv_chest_cloth_04\",\"queried\":38,\"name\":\"附魔师的长袍\",\"id\":17050},{\"icon\":\"inv_ore_ghostiron\",\"queried\":38,\"name\":\"幽冥铁矿石\",\"id\":72092},{\"icon\":\"inv_lessergronnmount_red\",\"queried\":38,\"name\":\"煤拳小戈隆\",\"id\":128311},{\"icon\":\"inv_crate_08\",\"queried\":37,\"name\":\"巨大的食人魔箱子\",\"id\":128313},{\"icon\":\"inv_sword_22\",\"queried\":37,\"name\":\"炫彩之剑\",\"id\":1604},{\"icon\":\"inv_misc_orb_02\",\"queried\":37,\"name\":\"欺诈宝珠\",\"id\":1973},{\"icon\":\"inv_gauntlets_28\",\"queried\":36,\"name\":\"破冰护手\",\"id\":22670},{\"icon\":\"spell_deathknight_summondeathcharger\",\"queried\":36,\"name\":\"血色死亡战马的缰绳\",\"id\":52200},{\"icon\":\"ability_mount_raptor\",\"queried\":35,\"name\":\"野蛮迅猛龙\",\"id\":69228},{\"icon\":\"inv_pants_04\",\"queried\":35,\"name\":\"踏云腿甲\",\"id\":14554},{\"icon\":\"spell_nature_acid_01\",\"queried\":35,\"name\":\"水之精华\",\"id\":7080},{\"icon\":\"inv_batpet\",\"queried\":34,\"name\":\"魔蝠幼崽\",\"id\":136924},{\"icon\":\"inv_shirt_05\",\"queried\":33,\"name\":\"新兵衬衣\",\"id\":23473},{\"icon\":\"inv_misc_book_08\",\"queried\":32,\"name\":\"战略论\",\"id\":71715},{\"icon\":\"inv_pants_wolf\",\"queried\":32,\"name\":\"战熊热裤\",\"id\":15065},{\"icon\":\"inv_egg_03\",\"queried\":32,\"name\":\"怪异的蛋\",\"id\":71636},{\"icon\":\"inv_misc_gem_topaz_01\",\"queried\":31,\"name\":\"奥术水晶\",\"id\":12363},{\"icon\":\"inv_pants_06\",\"queried\":31,\"name\":\"无双护腿\",\"id\":15431},{\"icon\":\"inv_scroll_03\",\"queried\":31,\"name\":\"图样：雷暴手套\",\"id\":21548},{\"icon\":\"inv_misc_volatilefire\",\"queried\":30,\"name\":\"动燃火焰\",\"id\":52325},{\"icon\":\"inv_enchant_alchemycatalyst\",\"queried\":30,\"name\":\"炼金催化剂\",\"id\":108996},{\"icon\":\"inv_scroll_06\",\"queried\":30,\"name\":\"设计图：灼热金剑\",\"id\":12261},{\"icon\":\"inv_misc_book_07\",\"queried\":28,\"name\":\"幻象之书：元素领主\",\"id\":138792},{\"icon\":\"inv_misc_crystalepic2\",\"queried\":28,\"name\":\"邪煞水晶\",\"id\":74248},{\"icon\":\"inv_misc_volatilewater\",\"queried\":28,\"name\":\"动燃之水\",\"id\":52326},{\"icon\":\"inv_ore_blackrock_ore\",\"queried\":28,\"name\":\"黑石矿石\",\"id\":109118},{\"icon\":\"inv_ore_trueironore\",\"queried\":28,\"name\":\"真铁矿石\",\"id\":109119},{\"icon\":\"inv_ingot_09\",\"queried\":28,\"name\":\"氪金锭\",\"id\":23449},{\"icon\":\"inv_shirt_14\",\"queried\":27,\"name\":\"刺客衬衣\",\"id\":2105},{\"icon\":\"inv_sword_01\",\"queried\":27,\"name\":\"巫术之刃\",\"id\":31336},{\"icon\":\"inv_ingot_ghostiron\",\"queried\":27,\"name\":\"幽冥铁块\",\"id\":72096},{\"icon\":\"inv_misc_fish_51\",\"queried\":27,\"name\":\"暗月刃喉鱼\",\"id\":124669},{\"icon\":\"inv_misc_book_07\",\"queried\":27,\"name\":\"秘典：变形术：龟\",\"id\":22739},{\"icon\":\"inv_chest_plate10\",\"queried\":26,\"name\":\"魔法瑟银胸甲\",\"id\":12618},{\"icon\":\"inv_misc_key_06\",\"queried\":26,\"name\":\"机械路霸\",\"id\":41508},{\"icon\":\"inv_misc_codexofxerrath_chains\",\"queried\":25,\"name\":\"失落军团的封印书典\",\"id\":92426},{\"icon\":\"inv_scroll_03\",\"queried\":25,\"name\":\"图样：雷暴护甲\",\"id\":15753},{\"icon\":\"inv_misc_volatileair\",\"queried\":25,\"name\":\"动燃空气\",\"id\":52328},{\"icon\":\"inv_misc_nativebeastskin\",\"queried\":24,\"name\":\"天然兽皮\",\"id\":110609},{\"icon\":\"inv_elemental_primal_mana\",\"queried\":23,\"name\":\"源生法力\",\"id\":22457},{\"icon\":\"inv_boots_plate_05\",\"queried\":23,\"name\":\"翡翠胫甲\",\"id\":14913},{\"icon\":\"inv_pants_03\",\"queried\":23,\"name\":\"前锋腿铠\",\"id\":14859},{\"icon\":\"inv_offhand_sunwell_d_01\",\"queried\":22,\"name\":\"幻象之书：外域\",\"id\":138789},{\"icon\":\"inv_misc_pet_pandaren_yeti_grey\",\"queried\":22,\"name\":\"格噜普斯的爪牙\",\"id\":128671},{\"icon\":\"inv_scroll_04\",\"queried\":22,\"name\":\"设计图：风暴头盔\",\"id\":23623},{\"icon\":\"inv_offhand_pvp330_d_02\",\"queried\":22,\"name\":\"幻象之书：诺森德\",\"id\":138790},{\"icon\":\"inv_misc_crystalepic2\",\"queried\":21,\"name\":\"灌魔水晶\",\"id\":71635},{\"icon\":\"inv_sword_08\",\"queried\":21,\"name\":\"氪金圣剑\",\"id\":23541},{\"icon\":\"ability_mount_onyxpanther\",\"queried\":21,\"name\":\"宝石玛瑙猎豹\",\"id\":82453},{\"icon\":\"inv_misc_bag_32\",\"queried\":21,\"name\":\"烬丝背包\",\"id\":54443},{\"icon\":\"inv_shirt_14\",\"queried\":20,\"name\":\"捕兽者衬衣\",\"id\":6136},{\"icon\":\"inv_sword_10\",\"queried\":20,\"name\":\"汉娜之刃\",\"id\":2801},{\"icon\":\"inv_scroll_06\",\"queried\":20,\"name\":\"图样：灰布外衣\",\"id\":14471},{\"icon\":\"inv_misc_book_07\",\"queried\":20,\"name\":\"秘典：变形术：北极熊宝宝\",\"id\":120137},{\"icon\":\"inv_elemental_primal_air\",\"queried\":20,\"name\":\"源生空气\",\"id\":22451},{\"icon\":\"inv_misc_volatilelife_green\",\"queried\":20,\"name\":\"动燃生命\",\"id\":52329},{\"icon\":\"inv_pants_02\",\"queried\":20,\"name\":\"勘察员的热裤\",\"id\":14565},{\"icon\":\"inv_offhand_1h_deathwingraid_d_01\",\"queried\":20,\"name\":\"幻象之书：大地的裂变\",\"id\":138791},{\"icon\":\"inv_scroll_03\",\"queried\":19,\"name\":\"设计图：奥金勇士剑\",\"id\":12834},{\"icon\":\"inv_scroll_05\",\"queried\":19,\"name\":\"图样：紫色丝质衬衣\",\"id\":4354},{\"icon\":\"ability_mount_seahorse\",\"queried\":19,\"name\":\"波塞冬斯的缰绳\",\"id\":67151},{\"icon\":\"inv_scroll_06\",\"queried\":19,\"name\":\"设计图：闪耀轻剑\",\"id\":12825}]", HotItem.class);

        // L.w(TAG, o);



       /*    b.getAllInfosFromFile(new RDFindListener<Auc>() {
            @Override
            public void onResult(List<Auc> aucs, boolean isError) {

            }
        });*/
        pushFragmentToStack(LoveFragment.class, null);
        RD.test("http://tv.aiseet.atianqi.com/i-tvbin/cfg/get_cfg?protocol_version=1&user_info=%7B%7D&format=json&version=0&need_client_ip=1&need_server_time=1&cfg_names=auto_switching_resolution%2Bcgi_preload_config%2Bperformance_testing_videos%2Bplugin_support%2Bplugin_list%2Bvip_config%2Bhttpdns_support%2Bvip_pay_config%2Bh5_test_url%2Buninstall_browser_config%2Bfeedback_qq_group%2Bauth_flag%2Btv_authentication%2Bis_app_version%2Bp2p_max_memory%2Bprojection_connect_config%2Bsplash_ad_logo%2Bpay_flow_control%2Bextend_cookie%2Bplayer_recommendation_control%2Bmulti_screen_config%2Bvideo_search_config%2Bvip_activity%2Bweb_ver_key%2Bdef_preload_config%2Bvip_intro_login_button_config%2Bvip_intro_unlogin_button_config%2Bsearch_phone_scan%2Bhevc_support%2Bprice_config%2Bvod_finish_threshold%2Bchild_ui_config%2Bh5_recommend_page_support%2Bpersonal_invalid_menu_tips%2Babout_app_description%2BPT%2BPR%2BAPPKEY%2BMTA_HOST%2BMTA_HOST_DEFAULT%2BCRASH_HOST%2BCRASH_HOST_DEFAULT%2BMTA_LOG_HOST%2BMTA_LOG_HOST_DEFAULT%2BMTA_MID_HOST%2BMTA_MID_HOST_DEFAULT%2BMTA_APP_ID%2BMTA_APP_KEY%2BVIDEO_DOMAIN%2BVIDEO_DOMAIN_DEFAULT%2BLICENSE_TAG%2BGUID_REQUEST_STRATEGY_TAG%2BUPGRADE_STRATEGY_TAG%2BSTATUSBAR_STRATEGY_TAG%2BMESSAGE_STRATEGY_TAG%2BAPP_INTEGRATE_TYPE%2BSPLASH_CONFIG%2BPerCenterShowList%2BEXIT_TAG%2BPLAYER_DOWNLOADER_MAX_USE_MEMORY%2BDEFINITION_4k_FLAG%2BLOGREPORT_URL%2BLOGREPORT_URL_DEFAULT%2BDEBUG_LOG%2BVOICE_CONTROL_TAG%2BPROJECTION_LONGPOLL%2BPROJECTION_CGI%2BCGI_PRELOAD_TAG%2BIS_CORP&guid=06EB5D1A00E081E142ED006B5603A65D&Q-UA=QV%3D1%26PR%3DVIDEO%26PT%3DSNMAPP%26CHID%3D10009%26RL%3D1920*1080%26VN%3D2.1.0%26VN_CODE%3D1400%26SV%3D5.1.1%26DV%3DJurassicPark%26VN_BUILD%3D0%26MD%3DMiBOX3%26BD%3DJurassicPark%26TVKPlatform%3D670603",
                new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        L.e(TAG, e);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        L.e(TAG, response);
                    }
                });
        startAnimActivity(UITestActivity.class);
        startAnimActivity(KActivityTest.class);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101) {
            mRealm.setText(BladeManager.getInstance().getCurrentRealmName());
        }
    }

    public void pushFragmentToStack(Class<?> cls, Bundle data) {
        BaseFragmentParam param = new BaseFragmentParam();
        param.cls = cls;
        param.data = data;
        goToThisFragment(param);
    }

    private void goToThisFragment(BaseFragmentParam param) {
        int containerId = getFragmentContainerId();
        Class<?> cls = param.cls;
        if (cls == null) {
            return;
        }
        try {
            String fragmentTag = getFragmentTag(param);
            FragmentManager fm = getSupportFragmentManager();
            KFragment fragment = (KFragment) fm.findFragmentByTag(fragmentTag);
            if (fragment == null) {
                fragment = (KFragment) cls.newInstance();
            }
            if (mCurrentFragment != null && mCurrentFragment != fragment) {
                mCurrentFragment.onLeave();
            }
            fragment.onEnter(param.data);

            FragmentTransaction ft = fm.beginTransaction();
            if (fragment.isAdded()) {
                Log.d("%s has been added", fragmentTag);
                ft.show(fragment);
            } else {
                Log.d("%s is added.", fragmentTag);
                ft.add(containerId, fragment, fragmentTag);
            }
            mCurrentFragment = fragment;
            ft.addToBackStack(fragmentTag);
            ft.commitAllowingStateLoss();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public void initViews() {

    }

    @Override
    public void init() {

    }

    @Override
    public void bindEvent() {

    }

    @Override
    public List<ItemData> parse(String response) {
        List<ItemData> items = new ArrayList<ItemData>();
        JSONArray array = JSON.parseArray(response);
        ItemData latestData = new ItemData();
        for (Object o : array) {
            JSONArray itemArray = JSON.parseArray(o.toString());
            ItemData itemData = new ItemData();
            itemData.price = itemArray.getLong(0);
            itemData.quality = itemArray.getShort(1);
            itemData.time = itemArray.getLong(2);
            if (itemData.time > latestData.time) {
                latestData = itemData;
            }
            items.add(itemData);
        }
        final ItemData finalLatestData = latestData;
    /*    SimpleTask.post(new Runnable() {
            @Override
            public void run() {
                recent.setText("最近" + finalLatestData.getPriceAndTime());
            }
        });*/

        Collections.sort(items, new Comparator<ItemData>() {
            @Override
            public int compare(ItemData lhs, ItemData rhs) {
                return (int) (lhs.price - rhs.price);
            }
        });
        return items;
    }

    @Override
    public void onClick(View v) {
        if (v instanceof Button) {
            startAnimActivity(ItemDataActivity.class, Item.ITEM_NAME, ((Button) v).getText().toString());
        }
    }
}
